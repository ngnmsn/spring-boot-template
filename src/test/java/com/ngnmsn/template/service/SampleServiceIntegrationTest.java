package com.ngnmsn.template.service;

import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.query.SampleSearchQuery;
import com.ngnmsn.template.domain.service.SampleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SampleServiceIntegrationTest {
    
    @Autowired
    private SampleService sampleService;
    
    @Test
    void shouldCreateSampleThroughService() {
        // Given
        var command = new SampleCreateCommand("サービステスト", 999);
        
        // When
        sampleService.create(command);
        
        // Then - Search for created sample
        var query = new SampleSearchQuery(null, "サービステスト", 1, 10);
        var results = sampleService.search(query);
        
        assertThat(results.getSampleResultList()).isNotEmpty();
        // Note: Due to existing test data, we check if our sample exists in results
        boolean found = results.getSampleResultList().stream()
            .anyMatch(result -> "サービステスト".equals(result.getText1()) && 999 == result.getNum1());
        assertThat(found).isTrue();
    }
    
    @Test
    void shouldSearchSamplesWithFilters() {
        // Given - Create test data
        sampleService.create(new SampleCreateCommand("テスト1", 100));
        sampleService.create(new SampleCreateCommand("テスト2", 200));
        sampleService.create(new SampleCreateCommand("別のテスト", 300));
        
        // When - Search with text filter
        var query = new SampleSearchQuery(null, "テスト1", 1, 10);
        var results = sampleService.search(query);
        
        // Then - Check if our test data exists in results
        boolean found = results.getSampleResultList().stream()
            .anyMatch(result -> "テスト1".equals(result.getText1()));
        assertThat(found).isTrue();
    }
    
    @Test
    void shouldReturnPaginatedResults() {
        // Given - Create multiple samples
        for (int i = 1; i <= 5; i++) {
            sampleService.create(new SampleCreateCommand("ページング" + i, i * 10));
        }
        
        // When - Get first page with 2 items per page
        var query = new SampleSearchQuery(null, "ページング", 1, 2);
        var results = sampleService.search(query);
        
        // Then - Check that pagination works (should have at most 2 per page)
        assertThat(results.getSampleResultList()).isNotEmpty();
        assertThat(results.getSampleResultList().size()).isLessThanOrEqualTo(2);
        
        // Check that our test data exists in results
        boolean foundPageSample = results.getSampleResultList().stream()
            .anyMatch(result -> result.getText1().startsWith("ページング"));
        assertThat(foundPageSample).isTrue();
    }
}