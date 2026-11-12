package com.example.providerregistry.service;

import com.example.providerregistry.client.ProviderClient;
import com.example.providerregistry.client.ProviderClientFactory;
import com.example.providerregistry.domain.PhoneAvailability;
import com.example.providerregistry.domain.Provider;
import com.example.providerregistry.registry.ProviderRegistry;
import org.springframework.stereotype.Service;

@Service
public class PhoneAvailabilityService {
    private final ProviderRegistry registry;
    private final ProviderClientFactory clientFactory;

    public PhoneAvailabilityService(ProviderRegistry registry, ProviderClientFactory clientFactory) {
        this.registry = registry;
        this.clientFactory = clientFactory;
    }

    public PhoneAvailability checkPhoneAvailability(String providerId, String phone) {
        Provider provider = registry.get(providerId);
        ProviderClient client = clientFactory.create(provider);
        return client.checkPhone(phone);
    }
}
