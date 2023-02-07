package ru.intervale.smev.utils;

import lombok.extern.log4j.Log4j2;
import ru.intervale.smev.model.InformationRequest;
import ru.intervale.smev.model.InformationResponse;
import ru.intervale.smev.model.Penalty;
import ru.intervale.smev.repo.InfoRequestRepo;
import ru.intervale.smev.repo.InfoResponseRepo;
import ru.intervale.smev.repo.PenaltyRepo;

import java.util.concurrent.Callable;

@Log4j2
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
    public InformationResponse call()  {
        log.info("Worker in progress..");
        infoRequestRepo.save(informationRequest);
        log.info("Information request: " + informationRequest);
        System.out.println("Worker" + Thread.currentThread().getName());

        InformationRequest temp = infoRequestRepo.findByVehicleCertificate(informationRequest.getVehicleCertificate());

        log.info("InformationRequest in call method is: " + temp);

        Penalty penalty = penaltyRepo.findPenaltyByVehicleCertificate(temp.getVehicleCertificate());
        InformationResponse tempResponse = new InformationResponse();

        tempResponse.setVehicleCertificate(penalty.getVehicleCertificate());
        tempResponse.setAccruedAmount(penalty.getAccruedAmount());
        tempResponse.setArticleKOAP(penalty.getArticleKOAP());
        tempResponse.setDate(penalty.getDate());
        tempResponse.setId(penalty.getId());
        tempResponse.setDecreeNumber(penalty.getDecreeNumber());
        tempResponse.setPaymentAmount(penalty.getPaymentAmount());

        infoResponseRepo.save(tempResponse);
        System.out.println("Worker stopped!");
        InformationResponse response = infoResponseRepo.getInformationResponseByVehicleCertificate(informationRequest.getVehicleCertificate());

        infoRequestRepo.delete(informationRequest);
        infoResponseRepo.delete(response);
        return response;
    }
}
