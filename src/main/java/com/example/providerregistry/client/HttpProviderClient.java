package com.example.providerregistry.client;

import com.example.providerregistry.client.transport.HttpTransport;
import com.example.providerregistry.domain.PhoneAvailability;
import com.example.providerregistry.domain.Provider;
import com.example.providerregistry.domain.exception.PhoneCheckException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HttpProviderClient implements ProviderClient {

    private final Provider provider;
    private final HttpTransport transport;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public HttpProviderClient(Provider provider, HttpTransport transport) {
        this.provider = provider;
        this.transport = transport;
    }

    @Override
    public PhoneAvailability checkPhone(String phone) {
        String encodedPhone = URLEncoder.encode(phone, StandardCharsets.UTF_8);
        URI uri = URI.create(provider.baseUrl() + "/subscribers/" + encodedPhone);

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Authorization", "Bearer " + provider.apiKey())
                .GET()
                .build();

        HttpResponse<String> response = transport.send(request);
        int status = response.statusCode();

        if (status == 200) {
            try {
                JsonNode root = objectMapper.readTree(response.body());
                if ("ok".equals(root.path("status").asText())) {
                    return PhoneAvailability.AVAILABLE;
                }
                throw new PhoneCheckException("Unexpected body: " + response.body());
            } catch (Exception e) {
                throw new PhoneCheckException("Failed to parse response", e);
            }
        } else if (status == 404) {
            return PhoneAvailability.UNAVAILABLE;
        } else {
            throw new PhoneCheckException("Unexpected HTTP status: " + status);
        }
    }
}
