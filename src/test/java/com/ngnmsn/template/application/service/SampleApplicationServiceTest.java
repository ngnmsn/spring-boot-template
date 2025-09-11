package com.ngnmsn.template.application.service;

import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.command.SampleUpdateCommand;
import com.ngnmsn.template.application.query.SampleSearchQuery;
import com.ngnmsn.template.application.exception.SampleNotFoundException;
import com.ngnmsn.template.application.exception.SampleValidationException;
import com.ngnmsn.template.domain.exception.SampleBusinessException;
import com.ngnmsn.template.domain.model.SampleId;
import com.ngnmsn.template.domain.model.SampleSearchCriteria;
import com.ngnmsn.template.domain.model.SampleSearchResults;
import com.ngnmsn.template.domain.model.SampleRecommendation;
import com.ngnmsn.template.domain.model.RecommendationType;
import com.ngnmsn.template.domain.model.SampleText;
import com.ngnmsn.template.domain.model.SampleNumber;
import com.ngnmsn.template.domain.model.CreatedAt;
import com.ngnmsn.template.domain.model.UpdatedAt;
import com.ngnmsn.template.domain.model.sample.DisplayId;
import com.ngnmsn.template.domain.model.sample.Sample;
import com.ngnmsn.template.domain.repository.SampleRepositoryPort;
import com.ngnmsn.template.domain.service.SampleDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SampleApplicationServiceTest {
    
    @Mock
    private SampleRepositoryPort sampleRepository;
    
    @Mock
    private SampleDomainService sampleDomainService;
    
    private SampleApplicationService applicationService;
    
    @BeforeEach
    void setUp() {
        applicationService = new SampleApplicationService(sampleRepository, sampleDomainService);
    }
    
    @Test
    void shouldCreateSampleSuccessfully() {
        // Given
        var command = new SampleCreateCommand("テスト", 123);
        var sample = createValidSample();
        var recommendations = Collections.<SampleRecommendation>emptyList();
        
        when(sampleDomainService.createSampleWithDuplicationCheck("テスト", 123))
            .thenReturn(sample);
        when(sampleDomainService.generateRecommendations(sample))
            .thenReturn(recommendations);
        
        // When
        var response = applicationService.createSample(command);
        
        // Then
        assertThat(response.getText1()).isEqualTo("テスト");
        assertThat(response.getNum1()).isEqualTo(123);
        verify(sampleRepository).save(sample);
    }
    
    @Test
    void shouldThrowValidationExceptionWhenCreateSampleWithInvalidInput() {
        // Given
        var command = new SampleCreateCommand("", null);
        
        when(sampleDomainService.createSampleWithDuplicationCheck("", null))
            .thenThrow(new IllegalArgumentException("入力値が不正です"));
        
        // When & Then
        assertThatThrownBy(() -> applicationService.createSample(command))
            .isInstanceOf(SampleValidationException.class)
            .hasMessageContaining("入力値が不正です");
    }
    
    @Test
    void shouldRethrowBusinessExceptionWhenCreateSample() {
        // Given
        var command = new SampleCreateCommand("重複テキスト", 123);
        
        when(sampleDomainService.createSampleWithDuplicationCheck("重複テキスト", 123))
            .thenThrow(new SampleBusinessException("同じテキストが既に存在します"));
        
        // When & Then
        assertThatThrownBy(() -> applicationService.createSample(command))
            .isInstanceOf(SampleBusinessException.class)
            .hasMessage("同じテキストが既に存在します");
    }
    
    @Test
    void shouldFindByIdSuccessfully() {
        // Given
        var id = 1L;
        var sampleId = new SampleId(id);
        var sample = createValidSample();
        var recommendations = List.of(
            new SampleRecommendation(RecommendationType.TEXT_OPTIMIZATION, "テキストを短縮してください")
        );
        
        when(sampleRepository.findById(sampleId)).thenReturn(Optional.of(sample));
        when(sampleDomainService.generateRecommendations(sample)).thenReturn(recommendations);
        
        // When
        var response = applicationService.findById(id);
        
        // Then
        assertThat(response).isNotNull();
        assertThat(response.getText1()).isEqualTo("テスト");
        assertThat(response.getRecommendations()).hasSize(1);
        assertThat(response.hasRecommendations()).isTrue();
        assertThat(response.hasRecommendationType(RecommendationType.TEXT_OPTIMIZATION)).isTrue();
    }
    
    @Test
    void shouldThrowExceptionWhenSampleNotFound() {
        // Given
        var id = 1L;
        when(sampleRepository.findById(new SampleId(id)))
            .thenReturn(Optional.empty());
        
        // When & Then
        assertThatThrownBy(() -> applicationService.findById(id))
            .isInstanceOf(SampleNotFoundException.class)
            .hasMessage("サンプルが見つかりません: " + id);
    }
    
    @Test
    void shouldSearchSuccessfully() {
        // Given
        var query = new SampleSearchQuery("001ABC", "テスト", 1, 10);
        var expectedResults = new SampleSearchResults(
            List.of(createValidSample()), 
            1, 1, 10
        );
        
        when(sampleRepository.search(any(SampleSearchCriteria.class)))
            .thenReturn(expectedResults);
        
        // When
        var results = applicationService.search(query);
        
        // Then
        assertThat(results).isEqualTo(expectedResults);
        verify(sampleRepository).search(any(SampleSearchCriteria.class));
    }
    
    @Test
    void shouldUpdateSampleSuccessfully() {
        // Given
        var id = 1L;
        var command = new SampleUpdateCommand("新しいテキスト", 999);
        var sample = createValidSample();
        
        when(sampleRepository.findById(new SampleId(id)))
            .thenReturn(Optional.of(sample));
        when(sampleDomainService.generateRecommendations(sample))
            .thenReturn(Collections.emptyList());
        
        // When
        var response = applicationService.updateSample(id, command);
        
        // Then
        verify(sampleRepository).save(sample);
        assertThat(response).isNotNull();
    }
    
    @Test
    void shouldUpdateSampleWithPartialData() {
        // Given
        var id = 1L;
        var command = new SampleUpdateCommand("新しいテキスト", null); // num1はnull
        var sample = createValidSample();
        
        when(sampleRepository.findById(new SampleId(id)))
            .thenReturn(Optional.of(sample));
        when(sampleDomainService.generateRecommendations(sample))
            .thenReturn(Collections.emptyList());
        
        // When
        var response = applicationService.updateSample(id, command);
        
        // Then
        verify(sampleRepository).save(sample);
        assertThat(response).isNotNull();
        // num1の更新は呼ばれないことを確認したい場合は、spyを使用
    }
    
    @Test
    void shouldThrowExceptionWhenUpdateNonExistentSample() {
        // Given
        var id = 1L;
        var command = new SampleUpdateCommand("新しいテキスト", 999);
        
        when(sampleRepository.findById(new SampleId(id)))
            .thenReturn(Optional.empty());
        
        // When & Then
        assertThatThrownBy(() -> applicationService.updateSample(id, command))
            .isInstanceOf(SampleNotFoundException.class)
            .hasMessage("サンプルが見つかりません: " + id);
    }
    
    @Test
    void shouldRethrowBusinessExceptionWhenUpdateSample() {
        // Given
        var id = 1L;
        var command = new SampleUpdateCommand("無効なテキスト", 999);
        var sample = spy(createValidSample());
        
        when(sampleRepository.findById(new SampleId(id)))
            .thenReturn(Optional.of(sample));
        doThrow(new SampleBusinessException("更新できません"))
            .when(sample).updateText("無効なテキスト");
        
        // When & Then
        assertThatThrownBy(() -> applicationService.updateSample(id, command))
            .isInstanceOf(SampleBusinessException.class)
            .hasMessage("更新できません");
    }
    
    @Test
    void shouldDeleteSampleSuccessfully() {
        // Given
        var id = 1L;
        var sampleId = new SampleId(id);
        var sample = createDeletableSample();
        
        when(sampleRepository.findById(sampleId))
            .thenReturn(Optional.of(sample));
        
        // When
        applicationService.deleteSample(id);
        
        // Then
        verify(sampleRepository).deleteById(sampleId);
    }
    
    @Test
    void shouldThrowExceptionWhenDeleteNonExistentSample() {
        // Given
        var id = 1L;
        when(sampleRepository.findById(new SampleId(id)))
            .thenReturn(Optional.empty());
        
        // When & Then
        assertThatThrownBy(() -> applicationService.deleteSample(id))
            .isInstanceOf(SampleNotFoundException.class)
            .hasMessage("サンプルが見つかりません: " + id);
    }
    
    @Test
    void shouldThrowExceptionWhenDeleteUndeletableSample() {
        // Given
        var id = 1L;
        var sampleId = new SampleId(id);
        var sample = createUndeletableSample();
        
        when(sampleRepository.findById(sampleId))
            .thenReturn(Optional.of(sample));
        
        // When & Then
        assertThatThrownBy(() -> applicationService.deleteSample(id))
            .isInstanceOf(SampleBusinessException.class)
            .hasMessage("このサンプルは削除できません。削除条件を満たしていません。");
    }
    
    @Test
    void shouldBulkDeleteSamplesSuccessfully() {
        // Given
        var ids = List.of(1L, 2L, 3L);
        var sampleIds = List.of(new SampleId(1L), new SampleId(2L), new SampleId(3L));
        
        // When
        applicationService.bulkDeleteSamples(ids);
        
        // Then
        verify(sampleDomainService).validateBulkDeletion(sampleIds);
        verify(sampleRepository, times(3)).deleteById(any(SampleId.class));
    }
    
    @Test
    void shouldThrowExceptionWhenBulkDeleteEmptyList() {
        // Given
        var emptyIds = Collections.<Long>emptyList();
        
        // When & Then
        assertThatThrownBy(() -> applicationService.bulkDeleteSamples(emptyIds))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("削除対象のIDが指定されていません");
    }
    
    @Test
    void shouldRethrowBusinessExceptionFromBulkDeleteValidation() {
        // Given
        var ids = List.of(1L, 2L);
        var sampleIds = List.of(new SampleId(1L), new SampleId(2L));
        
        doThrow(new SampleBusinessException("削除できないサンプルが含まれています"))
            .when(sampleDomainService).validateBulkDeletion(sampleIds);
        
        // When & Then
        assertThatThrownBy(() -> applicationService.bulkDeleteSamples(ids))
            .isInstanceOf(SampleBusinessException.class)
            .hasMessage("削除できないサンプルが含まれています");
        
        verify(sampleRepository, never()).deleteById(any(SampleId.class));
    }
    
    @Test
    void shouldIncrementSampleNumberSuccessfully() {
        // Given
        var id = 1L;
        var increment = 10;
        var sampleId = new SampleId(id);
        var sample = spy(createValidSample());
        
        when(sampleRepository.findById(sampleId))
            .thenReturn(Optional.of(sample));
        when(sampleDomainService.generateRecommendations(sample))
            .thenReturn(Collections.emptyList());
        
        // When
        var response = applicationService.incrementSampleNumber(id, increment);
        
        // Then
        verify(sample).incrementNumber(increment);
        verify(sampleRepository).save(sample);
        assertThat(response).isNotNull();
    }
    
    @Test
    void shouldThrowValidationExceptionWhenIncrementWithInvalidValue() {
        // Given
        var id = 1L;
        var increment = -1000; // 無効な値
        var sampleId = new SampleId(id);
        var sample = spy(createValidSample());
        
        when(sampleRepository.findById(sampleId))
            .thenReturn(Optional.of(sample));
        doThrow(new IllegalArgumentException("無効なインクリメント値"))
            .when(sample).incrementNumber(increment);
        
        // When & Then
        assertThatThrownBy(() -> applicationService.incrementSampleNumber(id, increment))
            .isInstanceOf(SampleValidationException.class)
            .hasMessageContaining("インクリメント値が不正です");
    }
    
    // Helper methods
    private Sample createValidSample() {
        var displayId = new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
        var text = new SampleText("テスト");
        var number = new SampleNumber(123);
        return new Sample(displayId, text, number);
    }
    
    private Sample createDeletableSample() {
        var sampleId = SampleId.generate();
        var displayId = new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
        var text = new SampleText("削除可能なテキスト");
        var number = new SampleNumber(123);
        var createdAt = new CreatedAt(java.time.LocalDateTime.now().minusDays(2)); // 2日前作成
        var updatedAt = UpdatedAt.now();
        var sample = spy(new Sample(sampleId, displayId, text, number, createdAt, updatedAt));
        when(sample.canBeDeleted()).thenReturn(true);
        return sample;
    }
    
    private Sample createUndeletableSample() {
        var sampleId = SampleId.generate();
        var displayId = new DisplayId("002ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
        var text = new SampleText("削除不可能なテキスト");
        var number = new SampleNumber(456);
        var createdAt = CreatedAt.now(); // 今作成
        var updatedAt = UpdatedAt.now();
        var sample = spy(new Sample(sampleId, displayId, text, number, createdAt, updatedAt));
        when(sample.canBeDeleted()).thenReturn(false);
        return sample;
    }
}