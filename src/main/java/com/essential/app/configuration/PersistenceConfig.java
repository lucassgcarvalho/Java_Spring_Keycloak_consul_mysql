package com.essential.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.essential.app.repository")
@Configuration
public class PersistenceConfig { 
}