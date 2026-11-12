package com.example.providerregistry.client.transport;

import com.example.providerregistry.domain.exception.PhoneCheckException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class JdkHttpTransport implements HttpTransport {
    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public HttpResponse<String> send(HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new PhoneCheckException("Network error", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PhoneCheckException("Request interrupted", e);
        }
    }
}
