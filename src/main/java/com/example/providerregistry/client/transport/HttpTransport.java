package com.example.providerregistry.client.transport;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface HttpTransport {
    HttpResponse<String> send(HttpRequest request);
}
