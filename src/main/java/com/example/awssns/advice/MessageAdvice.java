package com.example.awssns.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class MessageAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<String> handleBaseException(HttpServletRequest request, NullPointerException e) {
        log.error(String.valueOf(request.getMethod()));
        log.error(String.valueOf(request.getRequestURL()));
        return new ResponseEntity<>("GlobalExceptionHandler.handleException" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, IllegalArgumentException e) {
        log.error(String.valueOf(request.getRequestURL()));
        return new ResponseEntity<>("GlobalExceptionHandler.handleException" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
