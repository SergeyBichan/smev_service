package ru.intervale.smev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.smev.model.InformationRequest;
import ru.intervale.smev.model.InformationResponse;
import ru.intervale.smev.service.impl.InfoResponseServiceImpl;


@RequiredArgsConstructor
@RestController
public class SmevController {
    private final InfoResponseServiceImpl infoResponseServiceImpl;


    @PostMapping("/info")
    public ResponseEntity<InformationResponse> sendRequestFromAdapter(@RequestBody InformationRequest request) throws Exception {
        return new ResponseEntity<>(infoResponseServiceImpl.getInfoAboutPenalty(request), HttpStatus.OK);
    }
}
