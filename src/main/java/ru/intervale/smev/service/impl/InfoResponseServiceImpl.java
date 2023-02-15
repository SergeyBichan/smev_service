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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(3);
    }


    @Override
    public InformationResponse getInfoAboutPenalty(InformationRequest request) throws
            ApiRequestException {

        save(request);

        waitToDoSomething();

        log.warn("main " + Thread.currentThread().getId());
        Worker worker = new Worker(infoRequestRepo, penaltyRepo, infoResponseRepo, request);
        Future<InformationResponse> future = executorService.submit(worker);

        InformationResponse response;
        try {
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            ApiRequestException apiRequestException =
                    new ApiRequestException("Can't find any record about this certificate!");
            log.error(apiRequestException.getMessage());
            throw apiRequestException;
        }

        return response;
    }

    @Override
    public void save(InformationRequest request) {
        try {
            infoRequestRepo.save(request);
        } catch (Exception e) {
            throw new RuntimeException("Can't save this request " + request);
        }
    }

    @Override
    public void waitToDoSomething() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted exception!");
        }
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }
}
