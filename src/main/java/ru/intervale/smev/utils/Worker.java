package ru.intervale.smev.utils;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import ru.intervale.smev.controller.exception.ApiRequestException;
import ru.intervale.smev.model.InformationRequest;
import ru.intervale.smev.model.InformationResponse;
import ru.intervale.smev.model.Penalty;
import ru.intervale.smev.repo.InfoRequestRepo;
import ru.intervale.smev.repo.InfoResponseRepo;
import ru.intervale.smev.repo.PenaltyRepo;

import java.util.concurrent.Callable;

@Slf4j
public class Worker implements Callable<InformationResponse>, WorkerBehavior {

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

    @Transactional
    @Override
    public InformationResponse call() throws ApiRequestException {
        log.warn("Worker in progress..");
        log.debug("Information request: " + informationRequest);
        log.debug("Worker" + Thread.currentThread().getName());


        InformationRequest temp = getInformationRequest();

        log.debug("InformationRequest in call method is: " + temp);

        Penalty penalty = penaltyRepo.findPenaltyByVehicleCertificate(
                temp.getVehicleCertificate()
        );

        InformationResponse tempResponse = InformationResponse.builder()
                .vehicleCertificate(penalty.getVehicleCertificate())
                .accruedAmount(penalty.getAccruedAmount())
                .articleKOAP(penalty.getArticleKOAP())
                .date(penalty.getDate())
                .decreeNumber(penalty.getDecreeNumber())
                .paymentAmount(penalty.getPaymentAmount()).build();


        save(tempResponse);
        log.warn("Worker stopped!");
        InformationResponse response =
                infoResponseRepo.getInformationResponseByVehicleCertificate(informationRequest.getVehicleCertificate());

        delete(informationRequest);

        try {
            infoResponseRepo.delete(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public void save(InformationResponse response) {
        try {
            infoResponseRepo.save(response);
        } catch (Exception e) {
            throw new RuntimeException("Can't save this record to DB");
        }
    }

    @Override
    public void delete(InformationRequest request) {
        try {
            infoRequestRepo.delete(request);
        } catch (Exception e) {
            throw new RuntimeException("Can't delete this record from DB!");
        }
    }


    private InformationRequest getInformationRequest() {
        InformationRequest temp;
        try {
            temp = infoRequestRepo.findByVehicleCertificate(
                    informationRequest.getVehicleCertificate()
            );
        } catch (ApiRequestException e) {
            log.warn("Worker stopped!");
            throw new ApiRequestException("Can't find any record about this certificate! from Worker");
        }
        return temp;
    }
}
