package com.ngnmsn.template.domain.exception;

/**
 * Sample business exception
 */
public class SampleBusinessException extends RuntimeException {
    
    public SampleBusinessException(String message) {
        super(message);
    }
    
    public SampleBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}