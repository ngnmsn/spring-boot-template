package com.ngnmsn.template.infrastructure.config;

import com.ngnmsn.template.domain.repository.SampleRepositoryPort;
import com.ngnmsn.template.domain.service.SampleDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfrastructureConfig {
    
    @Bean
    public SampleDomainService sampleDomainService(SampleRepositoryPort sampleRepositoryPort) {
        return new SampleDomainService(sampleRepositoryPort);
    }
}