package com.useradministration.webapp.web.controller;

import com.useradministration.webapp.exception.NotValidPasswordException;
import com.useradministration.webapp.exception.NotValidUserDetailsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(NotValidPasswordException.class);

    @ExceptionHandler({NotValidPasswordException.class, NotValidUserDetailsException.class})
    protected ResponseEntity<Object> notValidpassword(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Something went wrong: " + ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}