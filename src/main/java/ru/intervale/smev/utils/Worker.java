package ru.intervale.smev.utils;

import lombok.extern.slf4j.Slf4j;
import ru.intervale.smev.controller.exception.ApiRequestException;
import ru.intervale.smev.model.InformationRequest;
import ru.intervale.smev.model.InformationResponse;
import ru.intervale.smev.model.Penalty;
import ru.intervale.smev.repo.InfoRequestRepo;
import ru.intervale.smev.repo.InfoResponseRepo;
import ru.intervale.smev.repo.PenaltyRepo;

import java.util.concurrent.Callable;

@Slf4j
public class Worker implements Callable<InformationResponse> {

    private final InfoRequestRepo infoRequestRepo;
    private final PenaltyRepo penaltyRepo;
    private final InfoResponseRepo infoResponseRepo;
    private final InformationRequest informationRequest;


    public Worker(InfoRequestRepo infoRequestRepo, PenaltyRepo penaltyRepo, InfoResponseRepo infoResponseRepo, InformationRequest informationRequest) {
        this.infoRequestRepo = infoRequestRepo;
        this.penaltyRepo = penaltyRepo;
        this.infoResponseRepo = infoResponseRepo;
        this.informationRequest = informationRequest;
    }

    @Override
    public InformationResponse call() throws ApiRequestException {
        log.warn("Worker in progress..");
        log.debug("Information request: " + informationRequest);
        log.debug("Worker" + Thread.currentThread().getName());


        InformationRequest temp;
        try {
            temp = infoRequestRepo.findByVehicleCertificate(
                    informationRequest.getVehicleCertificate()
            );
        } catch (ApiRequestException e) {
            log.warn("Worker stopped!");
            throw new ApiRequestException("Can't find any record about this certificate! from Worker");
        }

        log.debug("InformationRequest in call method is: " + temp);

        Penalty penalty = penaltyRepo.findPenaltyByVehicleCertificate(
                temp.getVehicleCertificate()
        );
        InformationResponse tempResponse = new InformationResponse();

        tempResponse.setVehicleCertificate(penalty.getVehicleCertificate());
        tempResponse.setAccruedAmount(penalty.getAccruedAmount());
        tempResponse.setArticleKOAP(penalty.getArticleKOAP());
        tempResponse.setDate(penalty.getDate());
        tempResponse.setId(penalty.getId());
        tempResponse.setDecreeNumber(penalty.getDecreeNumber());
        tempResponse.setPaymentAmount(penalty.getPaymentAmount());

        infoResponseRepo.save(tempResponse);
        log.warn("Worker stopped!");
        InformationResponse response;
        response = infoResponseRepo.getInformationResponseByVehicleCertificate(
                informationRequest.getVehicleCertificate()
        );
        infoRequestRepo.delete(informationRequest);
        infoResponseRepo.delete(response);
        return response;
    }
}
