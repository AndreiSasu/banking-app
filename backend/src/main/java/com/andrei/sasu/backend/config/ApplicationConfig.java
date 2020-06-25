package com.andrei.sasu.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.andrei.sasu.backend.repository")
public class ApplicationConfig {
}
