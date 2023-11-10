package com.soa.lab2soa.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class PersonExceptionHandler {
    @ExceptionHandler
    public ResponseStatusException handleBadRequestException(MethodArgumentNotValidException e) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getBody().getDetail().toString());
    }
}

