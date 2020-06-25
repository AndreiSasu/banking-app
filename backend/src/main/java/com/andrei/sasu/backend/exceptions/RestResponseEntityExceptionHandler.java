package com.andrei.sasu.backend.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.time.Instant;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private ObjectMapper objectMapper;

    public RestResponseEntityExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value
            = { AccountAlreadyExistsException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        ErrorMessage responseBody = new ErrorMessage(ex.getMessage(),
                Instant.now().getEpochSecond(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase());
        return handleExceptionInternal(ex, responseBody,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<Object> handleValidationException(ValidationException exception, WebRequest request) {
        ErrorMessage responseBody = new ErrorMessage(exception.getMessage(),
                Instant.now().getEpochSecond(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST,  request);
    }
}
