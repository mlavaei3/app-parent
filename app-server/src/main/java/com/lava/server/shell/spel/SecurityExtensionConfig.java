package com.lava.server.shell.spel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.spel.spi.EvaluationContextExtension;

@Configuration
public class SecurityExtensionConfig {

    @Bean
    public EvaluationContextExtension securityExtension() {
        return new MyExtension();
    }
}