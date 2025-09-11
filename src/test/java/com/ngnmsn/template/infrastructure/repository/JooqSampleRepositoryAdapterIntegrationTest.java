package com.ngnmsn.template.infrastructure.repository;

import com.ngnmsn.template.domain.repository.SampleRepositoryPort;
import com.ngnmsn.template.domain.model.SampleSearchCriteria;
import com.ngnmsn.template.domain.model.sample.DisplayId;
import com.ngnmsn.template.domain.model.sample.Sample;
import com.ngnmsn.template.domain.model.SampleText;
import com.ngnmsn.template.domain.model.SampleNumber;
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
        // Given
        var criteria = new SampleSearchCriteria(null, null, 1, 10);
        
        // When - Search for existing data
        var results = sampleRepository.search(criteria);
        
        // Then - Should return some results from test data
        assertThat(results).isNotNull();
        assertThat(results.getResults()).isNotNull();
    }
    
    @Test
    void shouldFindByDisplayId() {
        // Given
        var displayId = new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        
        // When - Search for existing sample by display ID
        var result = sampleRepository.findByDisplayId(displayId);
        
        // Then - Should find the existing sample
        assertThat(result).isPresent();
    }
    
    @Test 
    void shouldInsertNewSample() {
        // Given
        var displayId = new DisplayId("999TESTINTEGRATION999INTEGRATION999");
        var text = new SampleText("統合テスト");
        var num = new SampleNumber(999);
        var sample = new Sample(null, displayId, text, num, null, null);
        
        // When
        sampleRepository.save(sample);
        
        // Then - Should be able to find the inserted sample
        var found = sampleRepository.findByDisplayId(displayId);
        assertThat(found).isPresent();
        assertThat(found.get().getText1().getValue()).isEqualTo("統合テスト");
        assertThat(found.get().getNum1().getValue()).isEqualTo(999);
    }
}