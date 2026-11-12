package com.example.providerregistry.client;

import com.example.providerregistry.domain.Provider;

public interface ProviderClientFactory {
    ProviderClient create(Provider provider);
}
