package com.jmdc.mars.robots.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RobotExceptionHandler extends ResponseEntityExceptionHandler {

    // validation errors
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error(HttpStatus.BAD_REQUEST, "Malformed JSON request", ex);
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        ValidationError vError = new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        error.getSubErrors().add(vError);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(value= { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        Error error = new Error(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        return buildResponseEntity(error);
    }

    private ResponseEntity<Object> buildResponseEntity(Error apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
