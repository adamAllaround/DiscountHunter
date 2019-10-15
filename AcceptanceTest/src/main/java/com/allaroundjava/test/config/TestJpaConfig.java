package com.allaroundjava.test.config;

import com.allaroundjava.config.JpaConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Properties;

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

    @Override
    protected Properties createHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
        properties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }
}
