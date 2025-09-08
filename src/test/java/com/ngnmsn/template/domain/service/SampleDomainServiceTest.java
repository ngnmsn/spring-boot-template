package com.ngnmsn.template.domain.service;

import com.ngnmsn.template.domain.exception.SampleBusinessException;
import com.ngnmsn.template.domain.model.SampleSearchCriteria;
import com.ngnmsn.template.domain.model.SampleSearchResults;
import com.ngnmsn.template.domain.model.SampleText;
import com.ngnmsn.template.domain.model.SampleNumber;
import com.ngnmsn.template.domain.model.CreatedAt;
import com.ngnmsn.template.domain.model.UpdatedAt;
import com.ngnmsn.template.domain.model.sample.DisplayId;
import com.ngnmsn.template.domain.model.sample.Sample;
import com.ngnmsn.template.domain.model.SampleId;
import com.ngnmsn.template.domain.repository.SampleRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SampleDomainServiceTest {
    
    @Mock
    private SampleRepositoryPort sampleRepository;
    
    private SampleDomainService domainService;
    
    @BeforeEach
    void setUp() {
        domainService = new SampleDomainService(sampleRepository);
    }
    
    @Test
    void shouldGenerateUniqueDisplayId() {
        // Given
        when(sampleRepository.existsByDisplayId(any(DisplayId.class)))
            .thenReturn(false);
        
        // When
        var displayId = domainService.generateUniqueDisplayId();
        
        // Then
        assertThat(displayId).isNotNull();
        assertThat(displayId.getValue()).matches("^[0-9]{3}[A-Z]{30}$");
    }
    
    @Test
    void shouldRetryWhenDisplayIdAlreadyExists() {
        // Given
        when(sampleRepository.existsByDisplayId(any(DisplayId.class)))
            .thenReturn(true, true, false); // 3回目で重複なし
        
        // When
        var displayId = domainService.generateUniqueDisplayId();
        
        // Then
        assertThat(displayId).isNotNull();
        verify(sampleRepository, times(3)).existsByDisplayId(any(DisplayId.class));
    }
    
    @Test
    void shouldThrowExceptionWhenDisplayIdGenerationFailsAfterMaxAttempts() {
        // Given
        when(sampleRepository.existsByDisplayId(any(DisplayId.class)))
            .thenReturn(true); // 常に重複
        
        // When & Then
        assertThatThrownBy(() -> domainService.generateUniqueDisplayId())
            .isInstanceOf(SampleBusinessException.class)
            .hasMessage("表示IDの生成に失敗しました。システム管理者に連絡してください");
        
        verify(sampleRepository, times(11)).existsByDisplayId(any(DisplayId.class));
    }
    
    @Test
    void shouldCreateSampleWithoutDuplication() {
        // Given
        when(sampleRepository.existsByDisplayId(any(DisplayId.class)))
            .thenReturn(false);
        when(sampleRepository.search(any(SampleSearchCriteria.class)))
            .thenReturn(new SampleSearchResults(Collections.emptyList(), 0, 1, 10));
        
        // When
        var sample = domainService.createSampleWithDuplicationCheck("テスト", 123);
        
        // Then
        assertThat(sample.getText1().getValue()).isEqualTo("テスト");
        assertThat(sample.getNum1().getValue()).isEqualTo(123);
    }
    
    @Test
    void shouldThrowExceptionWhenTextIsDuplicate() {
        // Given
        var existingSample = createValidSample();
        when(sampleRepository.search(any(SampleSearchCriteria.class)))
            .thenReturn(new SampleSearchResults(List.of(existingSample), 1, 1, 10));
        
        // When & Then
        assertThatThrownBy(() -> domainService.createSampleWithDuplicationCheck("テスト", 123))
            .isInstanceOf(SampleBusinessException.class)
            .hasMessage("同じテキスト内容のサンプルが既に存在します: テスト");
    }
    
    @Test
    void shouldValidateBulkDeletionSuccessfully() {
        // Given
        var sampleId1 = SampleId.generate();
        var sampleId2 = SampleId.generate();
        var sample1 = createDeletableSample(sampleId1);
        var sample2 = createDeletableSample(sampleId2);
        
        when(sampleRepository.findById(sampleId1)).thenReturn(Optional.of(sample1));
        when(sampleRepository.findById(sampleId2)).thenReturn(Optional.of(sample2));
        
        // When & Then
        assertThatCode(() -> domainService.validateBulkDeletion(List.of(sampleId1, sampleId2)))
            .doesNotThrowAnyException();
    }
    
    @Test
    void shouldThrowExceptionWhenSampleNotFoundForBulkDeletion() {
        // Given
        var sampleId = SampleId.generate();
        when(sampleRepository.findById(sampleId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThatThrownBy(() -> domainService.validateBulkDeletion(List.of(sampleId)))
            .isInstanceOf(SampleBusinessException.class)
            .hasMessage("サンプルが見つかりません: " + sampleId);
    }
    
    @Test
    void shouldThrowExceptionWhenUndeletableSampleIncludedInBulkDeletion() {
        // Given
        var sampleId1 = SampleId.generate();
        var sampleId2 = SampleId.generate();
        var deletableSample = createDeletableSample(sampleId1);
        var undeletableSample = createUndeletableSample(sampleId2);
        
        when(sampleRepository.findById(sampleId1)).thenReturn(Optional.of(deletableSample));
        when(sampleRepository.findById(sampleId2)).thenReturn(Optional.of(undeletableSample));
        
        // When & Then
        assertThatThrownBy(() -> domainService.validateBulkDeletion(List.of(sampleId1, sampleId2)))
            .isInstanceOf(SampleBusinessException.class)
            .hasMessageContaining("削除できないサンプルが含まれています:");
    }
    
    @Test
    void shouldCalculateStatisticsWhenSamplesExist() {
        // Given
        var samples = List.of(
            createSampleWithValues("短いテキスト", 100),  // 短いテキスト、偶数
            createSampleWithValues("これは非常に長いテキストの例です。50文字を超える長さになります。", 150), // 長いテキスト、偶数
            createSampleWithValues("普通のテキスト", 75)   // 短いテキスト、奇数
        );
        var criteria = new SampleSearchCriteria(null, null, 1, 10);
        var searchResults = new SampleSearchResults(samples, 3, 1, 10);
        
        when(sampleRepository.search(criteria)).thenReturn(searchResults);
        
        // When
        var statistics = domainService.calculateStatistics(criteria);
        
        // Then
        assertThat(statistics.getTotalCount()).isEqualTo(3);
        assertThat(statistics.getAverageNumber()).isEqualTo(108.33, offset(0.01));
        assertThat(statistics.getLongTextCount()).isEqualTo(1);
        assertThat(statistics.getEvenNumberCount()).isEqualTo(2);
        assertThat(statistics.getLongTextPercentage()).isEqualTo(33.33, offset(0.01));
        assertThat(statistics.getEvenNumberPercentage()).isEqualTo(66.67, offset(0.01));
    }
    
    @Test
    void shouldReturnEmptyStatisticsWhenNoSamplesFound() {
        // Given
        var criteria = new SampleSearchCriteria(null, null, 1, 10);
        var searchResults = new SampleSearchResults(Collections.emptyList(), 0, 1, 10);
        
        when(sampleRepository.search(criteria)).thenReturn(searchResults);
        
        // When
        var statistics = domainService.calculateStatistics(criteria);
        
        // Then
        assertThat(statistics.getTotalCount()).isEqualTo(0);
        assertThat(statistics.getAverageNumber()).isEqualTo(0.0);
        assertThat(statistics.getLongTextCount()).isEqualTo(0);
        assertThat(statistics.getEvenNumberCount()).isEqualTo(0);
    }
    
    @Test
    void shouldGenerateRecommendationsForLongText() {
        // Given
        var sample = createSampleWithValues("これは非常に長いテキストの例です。50文字を超える長さになります。", 100);
        
        // When
        var recommendations = domainService.generateRecommendations(sample);
        
        // Then
        assertThat(recommendations).hasSize(1);
        assertThat(recommendations.get(0).getMessage()).contains("テキストが長すぎます");
    }
    
    @Test
    void shouldGenerateRecommendationsForLargeNumber() {
        // Given
        var sample = createSampleWithValues("短いテキスト", 6000);
        
        // When
        var recommendations = domainService.generateRecommendations(sample);
        
        // Then
        assertThat(recommendations).hasSize(1);
        assertThat(recommendations.get(0).getMessage()).contains("数値が大きすぎる可能性があります");
    }
    
    @Test
    void shouldGenerateRecommendationsForOldData() {
        // Given
        var oldCreatedAt = new CreatedAt(LocalDateTime.now().minusMonths(7));
        var sample = createSampleWithCreatedAt("テスト", 100, oldCreatedAt);
        
        // When
        var recommendations = domainService.generateRecommendations(sample);
        
        // Then
        assertThat(recommendations).hasSize(1);
        assertThat(recommendations.get(0).getMessage()).contains("古いデータです");
    }
    
    @Test
    void shouldGenerateMultipleRecommendations() {
        // Given
        var oldCreatedAt = new CreatedAt(LocalDateTime.now().minusMonths(8));
        var sample = createSampleWithCreatedAt("これは非常に長いテキストの例です。50文字を超える長さになります。", 7000, oldCreatedAt);
        
        // When
        var recommendations = domainService.generateRecommendations(sample);
        
        // Then
        assertThat(recommendations).hasSize(3);
    }
    
    @Test
    void shouldReturnEmptyRecommendationsForGoodSample() {
        // Given
        var sample = createSampleWithValues("適切なテキスト", 100);
        
        // When
        var recommendations = domainService.generateRecommendations(sample);
        
        // Then
        assertThat(recommendations).isEmpty();
    }
    
    // Helper methods
    private Sample createValidSample() {
        var displayId = new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
        var text = new SampleText("テスト");
        var number = new SampleNumber(123);
        return new Sample(displayId, text, number);
    }
    
    private Sample createDeletableSample(SampleId sampleId) {
        var displayId = new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
        var text = new SampleText("テスト");
        var number = new SampleNumber(123);
        var createdAt = new CreatedAt(LocalDateTime.now().minusDays(2)); // 2日前作成（削除可能）
        var updatedAt = UpdatedAt.now();
        return new Sample(sampleId, displayId, text, number, createdAt, updatedAt);
    }
    
    private Sample createUndeletableSample(SampleId sampleId) {
        var displayId = new DisplayId("002ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
        var text = new SampleText(""); // 空テキスト（削除不可）
        var number = new SampleNumber(456);
        var createdAt = CreatedAt.now(); // 今作成（削除不可）
        var updatedAt = UpdatedAt.now();
        return new Sample(sampleId, displayId, text, number, createdAt, updatedAt);
    }
    
    private Sample createSampleWithValues(String textValue, Integer numberValue) {
        var displayId = new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
        var text = new SampleText(textValue);
        var number = new SampleNumber(numberValue);
        return new Sample(displayId, text, number);
    }
    
    private Sample createSampleWithCreatedAt(String textValue, Integer numberValue, CreatedAt createdAt) {
        var sampleId = SampleId.generate();
        var displayId = new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
        var text = new SampleText(textValue);
        var number = new SampleNumber(numberValue);
        var updatedAt = UpdatedAt.now();
        return new Sample(sampleId, displayId, text, number, createdAt, updatedAt);
    }
}