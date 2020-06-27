package com.andrei.sasu.backend.config;

import com.andrei.sasu.backend.validation.BusinessTimesParser;
import com.andrei.sasu.backend.validation.WorkingDays;
import com.andrei.sasu.backend.validation.WorkingHours;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.text.ParseException;

@Configuration
@EnableJpaRepositories("com.andrei.sasu.backend.repository")
public class ApplicationConfig {

    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WorkingDays workingDays(@Value("${app.valid.working.days}") final String validWorkingDays) {
        return BusinessTimesParser.getWorkingDays(validWorkingDays);
    }

    @Bean
    public WorkingHours workingHours(@Value("${app.valid.working.hours}") final String validWorkingHours) {
        return BusinessTimesParser.getWorkingHours(validWorkingHours);
    }

}
