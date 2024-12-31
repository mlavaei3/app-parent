package com.lava.server.multipleauthapi.config;


import com.lava.server.multipleauthapi.model.error.BadRequestException;
import com.lava.server.multipleauthapi.model.error.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException e) {
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED
        );

        //log.error("AuthenticationException: ", e);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(BadRequestException e) {
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(),
                HttpStatus.BAD_REQUEST
        );

       // log.error("BadRequestException: ", e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

}