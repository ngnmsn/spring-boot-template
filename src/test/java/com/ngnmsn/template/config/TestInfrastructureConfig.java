package com.ngnmsn.template.config;

import com.ngnmsn.template.application.port.SampleRepositoryPort;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestInfrastructureConfig {
    
    @Bean
    @Primary
    public SampleRepositoryPort mockSampleRepositoryPort() {
        return Mockito.mock(SampleRepositoryPort.class);
    }
}