package ru.intervale.smev.controller.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime timeStamp;

    public ApiException(String message, HttpStatus httpStatus, LocalDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
