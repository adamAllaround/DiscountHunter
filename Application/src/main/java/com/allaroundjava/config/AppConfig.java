package com.allaroundjava.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.allaroundjava.price.extract", "com.allaroundjava.batch"})
public class AppConfig {
}
