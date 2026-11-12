package com.example.providerregistry.registry.json;

import com.example.providerregistry.domain.Provider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ProviderJsonReader {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Provider> read(File file) {
        try {
            return objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read providers from JSON: " + file.getAbsolutePath(), e);
        }
    }
}
