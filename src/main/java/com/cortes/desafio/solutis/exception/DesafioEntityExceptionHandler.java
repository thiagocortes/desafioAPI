package com.cortes.desafio.solutis.exception;

import java.util.Date;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class DesafioEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler(GenericException.class)
    public final ResponseEntity<Object> handleCustomException(GenericException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), new Date());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        String erro = "";
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            erro = violation.getPropertyPath() + ": " + violation.getMessage();
        }
        
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, erro, new Date());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
