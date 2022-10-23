package com.fda.management.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<ExceptionMessageDto> handleNoSuchElementException(NoSuchElementException ex) {
        return this.handleGeneralException(ex, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ExceptionMessageDto> handleGeneralException(Exception ex, HttpStatus httpStatus) {
        log.warn(ex.getMessage(), ex);
        return new ResponseEntity(new ExceptionMessageDto(ex), httpStatus);
    }


}

class ExceptionMessageDto {
    private String message;
    private String exceptionClassname;

    public ExceptionMessageDto(Throwable ex) {
        this.message = ex.getMessage();
        this.exceptionClassname = ex.getClass().getName();
    }
}

