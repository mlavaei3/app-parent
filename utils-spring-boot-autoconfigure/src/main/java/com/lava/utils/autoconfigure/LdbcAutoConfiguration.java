package com.lava.utils.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;

@AutoConfiguration
@ConditionalOnClass(Ldbc.class)
@EnableConfigurationProperties(LdbcProperties.class)
public class LdbcAutoConfiguration {

    private final LdbcProperties ldbcProperties;

    public LdbcAutoConfiguration(LdbcProperties ldbcProperties) {
        this.ldbcProperties = ldbcProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public Ldbc ldbc(@Lazy LdbcProperties ldbcProperties, JdbcTemplate jdbcTemplate) {
        return new Ldbc(ldbcProperties, jdbcTemplate);
    }

}