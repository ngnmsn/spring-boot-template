package com.ngnmsn.template.application.exception;

public class SampleApplicationException extends RuntimeException {

    public SampleApplicationException(String message) {
        super(message);
    }

    public SampleApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
