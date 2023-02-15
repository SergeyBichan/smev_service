package ru.intervale.smev.service;

import ru.intervale.smev.model.InformationRequest;
import ru.intervale.smev.model.InformationResponse;

import java.util.concurrent.ExecutionException;

public interface InfoResponseService {
    InformationResponse getInfoAboutPenalty(InformationRequest request) throws ExecutionException, InterruptedException;
    void save(InformationRequest request);
    void waitToDoSomething();
}
