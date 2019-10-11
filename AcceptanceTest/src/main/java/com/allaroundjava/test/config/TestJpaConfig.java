package com.allaroundjava.test.config;

import com.allaroundjava.config.JpaConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class TestJpaConfig extends JpaConfig {
    @Override
    @Bean
    protected DataSource createDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("org/springframework/batch/core/schema-h2.sql")
                .build();
    }
}