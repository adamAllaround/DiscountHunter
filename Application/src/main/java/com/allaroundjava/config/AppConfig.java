package com.allaroundjava.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.allaroundjava.price", "com.allaroundjava.batch", "com.allaroundjava.user", "com.allaroundjava.webpage"})
public class AppConfig {
}
