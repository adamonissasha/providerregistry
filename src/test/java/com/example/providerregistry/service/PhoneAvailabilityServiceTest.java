package com.example.providerregistry.service;

import com.example.providerregistry.client.ProviderClient;
import com.example.providerregistry.client.ProviderClientFactory;
import com.example.providerregistry.domain.PhoneAvailability;
import com.example.providerregistry.domain.Provider;
import com.example.providerregistry.registry.ProviderRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PhoneAvailabilityServiceTest {

    @Autowired
    private PhoneAvailabilityService service;

    @Autowired
    private ProviderRegistry registry;

    @MockBean
    private ProviderClientFactory clientFactory;

    @BeforeEach
    void setUp() {
        registry.replaceAll(List.of(new Provider("test-provider", "http://fake", "key")));
    }

    @Test
    void shouldReturnAvailable_WhenProviderReturnsOk() {
        ProviderClient mockClient = mock(ProviderClient.class);
        when(mockClient.checkPhone(anyString())).thenReturn(PhoneAvailability.AVAILABLE);
        when(clientFactory.create(any(Provider.class))).thenReturn(mockClient);

        PhoneAvailability result = service.checkPhoneAvailability("test-provider", "+79991234567");

        assertEquals(PhoneAvailability.AVAILABLE, result);
    }

    @Test
    void shouldReturnUnavailable_WhenProviderReturns404() {
        ProviderClient mockClient = mock(ProviderClient.class);
        when(mockClient.checkPhone(anyString())).thenReturn(PhoneAvailability.UNAVAILABLE);
        when(clientFactory.create(any(Provider.class))).thenReturn(mockClient);

        PhoneAvailability result = service.checkPhoneAvailability("test-provider", "+79991234567");

        assertEquals(PhoneAvailability.UNAVAILABLE, result);
    }
}
