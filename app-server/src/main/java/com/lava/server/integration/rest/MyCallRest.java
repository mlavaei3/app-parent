package com.lava.server.integration.rest;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MyCallRest {
    @MessagingGateway
    public interface BaseCallRest {
        @Gateway(requestChannel = "callingTest.input")
        Message<String> callTest(Message voidMessage);

    }

    @Bean
    public IntegrationFlow callingTest() {
        return f -> f
                .handle(Http.outboundGateway("http://localhost:7070/api/test")
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class))
                ;
    }
}
