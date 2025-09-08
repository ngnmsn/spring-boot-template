package com.ngnmsn.template.infrastructure.config;

import com.ngnmsn.template.application.port.SampleRepositoryPort;
import com.ngnmsn.template.infrastructure.repository.LegacySampleRepositoryAdapter;
import com.ngnmsn.template.repository.SampleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfrastructureConfig {
    
    @Bean
    public SampleRepositoryPort sampleRepositoryPort(SampleRepository sampleRepository) {
        return new LegacySampleRepositoryAdapter(sampleRepository);
    }
}