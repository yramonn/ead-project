package com.ead.paymentservice.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @LoadBalanced
    @Bean
    public RestClient.Builder RestClientBuilder() {
        return RestClient.builder();

    }
}
