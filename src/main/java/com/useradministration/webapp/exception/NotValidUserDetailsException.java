package com.useradministration.webapp.exception;

public class NotValidUserDetailsException extends RuntimeException {

    public NotValidUserDetailsException() {
    }

    public NotValidUserDetailsException(String message) {
        super(message);

    }
}