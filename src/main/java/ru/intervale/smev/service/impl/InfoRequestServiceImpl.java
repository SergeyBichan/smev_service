package ru.intervale.smev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.intervale.smev.model.InformationRequest;
import ru.intervale.smev.model.InformationResponse;
import ru.intervale.smev.repo.InfoRequestRepo;
import ru.intervale.smev.repo.InfoResponseRepo;
import ru.intervale.smev.repo.PenaltyRepo;
import ru.intervale.smev.service.InfoResponseService;
import ru.intervale.smev.utils.Worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Service
@RequiredArgsConstructor
public class InfoRequestServiceImpl implements InfoResponseService {
    private final InfoRequestRepo infoRequestRepo;
    private final PenaltyRepo penaltyRepo;
    private final InfoResponseRepo infoResponseRepo;


    @Override
    public InformationResponse getInfoAboutPenalty(InformationRequest request) throws Exception {
        System.out.println("main" + Thread.currentThread().getName());
        Worker worker = new Worker(infoRequestRepo,penaltyRepo,infoResponseRepo,request );
        ExecutorService service = Executors.newFixedThreadPool(3);
        Future<InformationResponse> future = service.submit(worker);
        return future.get();
}}
