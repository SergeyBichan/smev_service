package ru.intervale.smev.service;

import ru.intervale.smev.model.InformationRequest;

public interface InfoRequestService {
    InformationRequest createInfoRequest(InformationRequest request);
    Iterable<InformationRequest> getAllRequests();

    InformationRequest getPenalty(InformationRequest request);
}
