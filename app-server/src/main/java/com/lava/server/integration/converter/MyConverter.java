package com.lava.server.integration.converter;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.stereotype.Component;

@Component
public class MyConverter {
    @MessagingGateway
    public interface BaseConverter {

        @Gateway(requestChannel = "toUpper.input")
        String toUpperCase(String term);

        @Gateway(requestChannel = "toLower.input")
        String toLowerCase(String term);
    }


    @Bean
    public IntegrationFlow toUpper() {
        return f -> f
                .handle((payload, headers) -> payload)
                .<String, String>transform(String::toUpperCase)
                ;
    }

    @Bean
    public IntegrationFlow toLower() {
        return f -> f
                .handle((payload, headers) -> payload)
                .<String, String>transform(String::toLowerCase)
                ;
    }
}
