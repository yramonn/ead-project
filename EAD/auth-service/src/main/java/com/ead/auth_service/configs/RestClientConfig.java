package com.ead.auth_service.configs;

import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    static final int TIMEOUT = 5000;

    @LoadBalanced
    @Bean
    public RestClient.Builder RestClientBuilder() {
        return RestClient.builder().requestFactory(customerRequestFactory());
    }

    ClientHttpRequestFactory customerRequestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofMillis(TIMEOUT))
                .withReadTimeout(Duration.ofMillis(TIMEOUT));
        return ClientHttpRequestFactories.get(settings);
    }

    
}
