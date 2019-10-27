package com.allaroundjava.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableBatchProcessing
@EnableJpaRepositories(basePackages = {"com.allaroundjava.batch", "com.allaroundjava.user"
        , "com.allaroundjava.webpage", "com.allaroundjava.price"})
@ComponentScan(basePackages = {"com.allaroundjava.price", "com.allaroundjava.batch", "com.allaroundjava.user", "com.allaroundjava.webpage", "com.allaroundjava.rest.webpage", "com.allaroundjava.authentication"})
public class AppConfig {
}
