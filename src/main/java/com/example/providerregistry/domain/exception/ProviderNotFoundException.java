package com.example.providerregistry.domain.exception;

public class ProviderNotFoundException extends RuntimeException {
    public ProviderNotFoundException(String id) {
        super("Provider not found: " + id);
    }
}
