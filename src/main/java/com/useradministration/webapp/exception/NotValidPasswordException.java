package com.useradministration.webapp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "!!!!!!!!incorrect password!!!!!!!!!!!!")
@SuppressWarnings("serial")
public class NotValidPasswordException extends RuntimeException {

    public NotValidPasswordException() {
    }

    public NotValidPasswordException(String message) {
        super(message);
    }

}
