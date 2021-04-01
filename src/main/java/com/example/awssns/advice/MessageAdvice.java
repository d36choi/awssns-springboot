package com.example.awssns.advice;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.sns.model.SnsException;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;

@Slf4j
@RestControllerAdvice
public class MessageAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(HttpServletRequest request, NullPointerException e) {
        printError(request, e);
        return new ResponseEntity<>("GlobalExceptionHandler.handleException " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalException(HttpServletRequest request, IllegalArgumentException e) {
        printError(request, e);
        return new ResponseEntity<>("GlobalExceptionHandler.IllegalArgumentException " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = JsonProcessingException.class)
    public ResponseEntity<String> handleJsonException(HttpServletRequest request, JsonProcessingException e) {
        printError(request, e);
        return new ResponseEntity<>("GlobalExceptionHandler.JsonProcessingException " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvalidParameterException.class)
    public ResponseEntity<String> handleJsonException(HttpServletRequest request, InvalidParameterException e) {
        printError(request, e);
        return new ResponseEntity<>("GlobalExceptionHandler.InvalidParameterException " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
        printError(request, e);
        return new ResponseEntity<>("GlobalExceptionHandler.HttpMessageNotReadableException " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void printError(HttpServletRequest request, Exception e) {
        log.error(String.format("%s > %s", request.getRequestURL(), e.getMessage()));
    }

    @ExceptionHandler(value = SnsException.class)
    public ResponseEntity<String> handleSnsException(HttpServletRequest request, SnsException e) {
        printSnsError(request, e);
        return new ResponseEntity<>("GlobalExceptionHandler.SnsException " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private void printSnsError(HttpServletRequest request, SnsException e) {
        log.error(String.format("%s > %s", request.getRequestURL(), e.awsErrorDetails().errorMessage()));
    }
}
