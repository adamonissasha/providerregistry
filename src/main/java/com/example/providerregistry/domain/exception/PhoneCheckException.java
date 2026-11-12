package com.example.providerregistry.domain.exception;

public class PhoneCheckException extends RuntimeException {
    public PhoneCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneCheckException(String message) {
        super(message);
    }
}
