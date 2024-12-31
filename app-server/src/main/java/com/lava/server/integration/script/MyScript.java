package com.lava.server.integration.script;

import org.springframework.context.annotation.Bean;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageProcessorSpec;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class MyScript {
    @MessagingGateway
    public interface BaseScript {
        @Gateway(requestChannel = "eval.input")
        String eval(String script);
    }

    @Bean
    public IntegrationFlow eval() {
        return f -> f
                .handle((payload, headers) -> {
                    ExpressionParser parser = new SpelExpressionParser();
                    Expression exp = parser.parseExpression(payload.toString());
                    Object message =  exp.getValue();
                    return message;
                })
                .<Object, Class<?>>route(Object::getClass, m -> m
                        .channelMapping(String.class, "channelStr")
                        .channelMapping(Integer.class, "channelInt"))
                ;
    }

    @Bean
    public IntegrationFlow getInt(){
        return IntegrationFlow.from("channelInt")
                .transform(message -> message.toString()+ " channelInt")
                .get();
    }

    @Bean
    public IntegrationFlow getStr(){
        return IntegrationFlow.from("channelStr")
                .transform(message -> message +" channelStr")
                .get();
    }
}
