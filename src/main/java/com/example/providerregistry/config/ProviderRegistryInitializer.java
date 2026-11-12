package com.example.providerregistry.config;

import com.example.providerregistry.domain.Provider;
import com.example.providerregistry.registry.ProviderRegistry;
import com.example.providerregistry.registry.json.ProviderJsonReader;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class ProviderRegistryInitializer implements ApplicationRunner {

    private final ProviderRegistry registry;
    private final ProviderJsonReader jsonReader;

    public ProviderRegistryInitializer(ProviderRegistry registry, ProviderJsonReader jsonReader) {
        this.registry = registry;
        this.jsonReader = jsonReader;
    }

    @Override
    public void run(ApplicationArguments args) {
        File file = new File("providers.json");
        if (file.exists()) {
            List<Provider> providers = jsonReader.read(file);
            registry.replaceAll(providers);
            System.out.println("Загружено поставщиков: " + providers.size());
        } else {
            System.out.println("Файл providers.json не найден. Реестр пуст.");
        }
    }
}
