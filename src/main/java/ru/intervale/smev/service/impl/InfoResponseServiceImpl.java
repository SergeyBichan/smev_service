package ru.intervale.smev.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.intervale.smev.controller.exception.ApiRequestException;
import ru.intervale.smev.model.InformationRequest;
import ru.intervale.smev.model.InformationResponse;
import ru.intervale.smev.repo.InfoRequestRepo;
import ru.intervale.smev.repo.InfoResponseRepo;
import ru.intervale.smev.repo.PenaltyRepo;
import ru.intervale.smev.service.InfoResponseService;
import ru.intervale.smev.utils.Worker;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Slf4j
@Service
@RequiredArgsConstructor
public class InfoResponseServiceImpl implements InfoResponseService {
    private final InfoRequestRepo infoRequestRepo;
    private final PenaltyRepo penaltyRepo;
    private final InfoResponseRepo infoResponseRepo;


    @Override
    public InformationResponse getInfoAboutPenalty(InformationRequest request) throws
            ApiRequestException {
            infoRequestRepo.save(request);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted exception!");
        }
        log.warn("main " + Thread.currentThread().getId());
        Worker worker = new Worker(infoRequestRepo,penaltyRepo,infoResponseRepo,request );
        ExecutorService service = Executors.newFixedThreadPool(3);
        Future<InformationResponse> future = service.submit(worker);

        InformationResponse response;
        try {
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            ApiRequestException apiRequestException =
                    new ApiRequestException("Can't find any record about this certificate!");
            log.error(apiRequestException.getMessage());
            throw new ApiRequestException(apiRequestException.getMessage());
        }

        return response;
}}
