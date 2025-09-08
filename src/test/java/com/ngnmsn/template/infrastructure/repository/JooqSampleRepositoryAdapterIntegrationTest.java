package com.ngnmsn.template.infrastructure.repository;

import com.ngnmsn.template.application.port.SampleRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JooqSampleRepositoryAdapterIntegrationTest {
    
    @Autowired
    private SampleRepositoryPort sampleRepository;
    
    @Test
    void shouldSearchExistingData() {
        // When - Search for existing data
        var results = sampleRepository.search(null, null, 1, 10);
        
        // Then - Should return some results from test data
        assertThat(results).isNotNull();
        assertThat(results.getSampleResultList()).isNotNull();
    }
    
    @Test
    void shouldFindByDisplayId() {
        // When - Search for existing sample by display ID
        var result = sampleRepository.findByDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        
        // Then - Should find the existing sample
        assertThat(result).isNotNull();
    }
    
    @Test 
    void shouldInsertNewSample() {
        // Given
        String displayId = "999TESTINTEGRATION999INTEGRATION999";
        String text = "統合テスト";
        Integer num = 999;
        
        // When
        sampleRepository.insert(displayId, text, num);
        
        // Then - Should be able to find the inserted sample
        var found = sampleRepository.findByDisplayId(displayId);
        assertThat(found).isNotNull();
        assertThat(found.getText1()).isEqualTo("統合テスト");
        assertThat(found.getNum1()).isEqualTo(999);
    }
}