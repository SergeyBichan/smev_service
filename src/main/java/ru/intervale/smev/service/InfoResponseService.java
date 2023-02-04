package ru.intervale.smev.service;

import ru.intervale.smev.model.InformationRequest;
import ru.intervale.smev.model.InformationResponse;

public interface InfoResponseService {
    InformationResponse getInfoAboutPenalty(InformationRequest request) throws Exception;
}
