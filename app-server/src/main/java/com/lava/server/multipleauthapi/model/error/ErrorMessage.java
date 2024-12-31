package com.lava.server.multipleauthapi.model.error;

import com.lava.server.multipleauthapi.config.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


public class ErrorMessage {

    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDateTime time = LocalDateTime.now();

    private String message;

    private HttpStatus httpStatus;

    public ErrorMessage(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}