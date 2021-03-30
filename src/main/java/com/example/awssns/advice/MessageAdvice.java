package com.example.awssns.advice;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;

@Slf4j
@RestControllerAdvice
public class MessageAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(HttpServletRequest request, NullPointerException e) {
        log.error(String.valueOf(request.getMethod()));
        log.error(String.valueOf(request.getRequestURL()));
        return new ResponseEntity<>("GlobalExceptionHandler.handleException" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalException(HttpServletRequest request, IllegalArgumentException e) {
        log.error(String.valueOf(request.getRequestURL()));
        return new ResponseEntity<>("GlobalExceptionHandler.IllegalArgumentException" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = JsonProcessingException.class)
    public ResponseEntity<String> handleJsonException(HttpServletRequest request, JsonProcessingException e) {
        log.error(String.valueOf(request.getRequestURL()));
        return new ResponseEntity<>("GlobalExceptionHandler.JsonProcessingException" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = InvalidParameterException.class)
    public ResponseEntity<String> handleJsonException(HttpServletRequest request, InvalidParameterException e) {
        log.error(String.valueOf(request.getRequestURL()));
        return new ResponseEntity<>("GlobalExceptionHandler.InvalidParameterException" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
