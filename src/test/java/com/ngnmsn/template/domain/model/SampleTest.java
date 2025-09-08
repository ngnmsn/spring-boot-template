package com.ngnmsn.template.domain.model;

import com.ngnmsn.template.domain.exception.SampleBusinessException;
import com.ngnmsn.template.domain.model.sample.DisplayId;
import com.ngnmsn.template.domain.model.sample.Sample;
import com.ngnmsn.template.domain.model.sample.SampleId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SampleTest {
    
    @Test
    void shouldCreateSampleSuccessfully() {
        // Given
        var displayId = new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
        var text = new SampleText("テストテキスト");
        var number = new SampleNumber(123);
        
        // When
        var sample = new Sample(displayId, text, number);
        
        // Then
        assertThat(sample.getDisplayId()).isEqualTo(displayId);
        assertThat(sample.getText1()).isEqualTo(text);
        assertThat(sample.getNum1()).isEqualTo(number);
        assertThat(sample.getId()).isNotNull();
        assertThat(sample.getCreatedAt()).isNotNull();
        assertThat(sample.getUpdatedAt()).isNotNull();
    }
    
    @Test
    void shouldUpdateTextSuccessfully() {
        // Given
        var sample = createValidSample();
        var newText = "新しいテキスト";
        
        // When
        sample.updateText(newText);
        
        // Then
        assertThat(sample.getText1().getValue()).isEqualTo(newText);
    }
    
    @Test
    void shouldThrowExceptionWhenUpdateTextIsEmpty() {
        // Given
        var sample = createValidSample();
        
        // When & Then
        assertThrows(SampleBusinessException.class, 
            () -> sample.updateText(""));
    }
    
    @Test
    void shouldThrowExceptionWhenUpdateTextIsSame() {
        // Given
        var sample = createValidSample();
        var currentText = sample.getText1().getValue();
        
        // When & Then
        assertThrows(SampleBusinessException.class, 
            () -> sample.updateText(currentText));
    }
    
    @Test
    void shouldUpdateNumberSuccessfully() {
        // Given
        var sample = createValidSample();
        
        // When
        sample.updateNumber(999);
        
        // Then
        assertThat(sample.getNum1().getValue()).isEqualTo(999);
    }
    
    @Test
    void shouldReturnTrueWhenCanBeDeleted() {
        // Given
        var sample = createValidSample();
        
        // When
        boolean canDelete = sample.canBeDeleted();
        
        // Then
        assertTrue(canDelete);
    }
    
    @Test
    void shouldIncrementNumberCorrectly() {
        // Given
        var sample = createValidSample();
        var originalValue = sample.getNum1().getValue();
        
        // When
        sample.incrementNumber(10);
        
        // Then
        assertThat(sample.getNum1().getValue()).isEqualTo(originalValue + 10);
    }
    
    private Sample createValidSample() {
        return new Sample(
            new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234"),
            new SampleText("テスト"),
            new SampleNumber(100)
        );
    }
}