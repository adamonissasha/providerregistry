package com.example.providerregistry.client;

import com.example.providerregistry.client.transport.HttpTransport;
import com.example.providerregistry.domain.PhoneAvailability;
import com.example.providerregistry.domain.Provider;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HttpProviderClientTest {

    private final Provider provider = new Provider("p1", "http://test.com", "api-key");

    @Test
    void shouldReturnAvailableOn200AndStatusOk() {
        HttpTransport transport = mock(HttpTransport.class);
        HttpResponse<String> response = mockResponse(200, "{\"status\":\"ok\"}");
        when(transport.send(any())).thenReturn(response);

        HttpProviderClient client = new HttpProviderClient(provider, transport);

        assertEquals(PhoneAvailability.AVAILABLE, client.checkPhone("79991234567"));
    }

    @Test
    void shouldReturnUnavailableOn404() {
        HttpTransport transport = mock(HttpTransport.class);
        HttpResponse<String> response = mockResponse(404, "");
        when(transport.send(any())).thenReturn(response);

        HttpProviderClient client = new HttpProviderClient(provider, transport);

        assertEquals(PhoneAvailability.UNAVAILABLE, client.checkPhone("79991234567"));
    }

    @SuppressWarnings("unchecked")
    private HttpResponse<String> mockResponse(int statusCode, String body) {
        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(statusCode);
        when(response.body()).thenReturn(body);
        return response;
    }
}
