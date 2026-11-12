package com.example.providerregistry.client;

import com.example.providerregistry.domain.PhoneAvailability;

public interface ProviderClient {
    PhoneAvailability checkPhone(String phone);
}
