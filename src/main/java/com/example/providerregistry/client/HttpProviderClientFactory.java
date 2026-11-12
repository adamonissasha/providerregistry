package com.example.providerregistry.client;

import com.example.providerregistry.client.transport.HttpTransport;
import com.example.providerregistry.domain.Provider;
import org.springframework.stereotype.Component;

@Component
public class HttpProviderClientFactory implements ProviderClientFactory {
    private final HttpTransport transport;

    public HttpProviderClientFactory(HttpTransport transport) {
        this.transport = transport;
    }

    @Override
    public ProviderClient create(Provider provider) {
        return new HttpProviderClient(provider, transport);
    }
}
