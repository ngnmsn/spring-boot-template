package com.ngnmsn.template.application.exception;

public class SampleValidationException extends RuntimeException {
    
    public SampleValidationException(String message) {
        super(message);
    }
    
    public SampleValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}