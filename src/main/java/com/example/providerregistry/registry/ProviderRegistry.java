package com.example.providerregistry.registry;

import com.example.providerregistry.domain.Provider;
import com.example.providerregistry.domain.exception.ProviderNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProviderRegistry {
    private volatile Map<String, Provider> providers = Map.of();

    public void replaceAll(Collection<Provider> newProviders) {
        this.providers = newProviders.stream()
                .collect(Collectors.toUnmodifiableMap(Provider::id, p -> p));
    }

    public Provider get(String id) {
        Provider provider = providers.get(id);
        if (provider == null) {
            throw new ProviderNotFoundException(id);
        }
        return provider;
    }

    public Collection<Provider> getAll() {
        return providers.values();
    }
}
