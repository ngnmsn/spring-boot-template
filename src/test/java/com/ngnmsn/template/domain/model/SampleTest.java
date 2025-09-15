package com.ngnmsn.template.domain.model;

import com.ngnmsn.template.domain.exception.SampleBusinessException;
import com.ngnmsn.template.domain.model.sample.DisplayId;
import com.ngnmsn.template.domain.model.sample.Sample;
import com.ngnmsn.template.domain.model.SampleId;
import com.ngnmsn.template.domain.model.CreatedAt;
import com.ngnmsn.template.domain.model.UpdatedAt;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        // Given - 1日前の日時を指定
        var pastCreatedAt = new CreatedAt(LocalDateTime.now().minusDays(2));
        var sample = new Sample(
            new SampleId(1L),
            new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234"),
            new SampleText("テスト"),
            new SampleNumber(100),
            pastCreatedAt,
            UpdatedAt.now()
        );
        
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
    
    @Test
    void shouldThrowExceptionWhenConstructorArgumentsAreNull() {
        var displayId = new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
        var text = new SampleText("テスト");
        var number = new SampleNumber(100);
        
        assertThrows(NullPointerException.class, 
            () -> new Sample(null, text, number));
        assertThrows(NullPointerException.class, 
            () -> new Sample(displayId, null, number));
        assertThrows(NullPointerException.class, 
            () -> new Sample(displayId, text, null));
    }
    
    @Test
    void shouldUpdateUpdatedAtWhenTextIsUpdated() {
        var sample = createValidSample();
        var originalUpdatedAt = sample.getUpdatedAt();
        
        // 時間差を作るため少し待機
        try { Thread.sleep(10); } catch (InterruptedException e) {}
        
        sample.updateText("新しいテキスト");
        
        assertThat(sample.getUpdatedAt().getValue())
            .isAfter(originalUpdatedAt.getValue());
    }
    
    @Test
    void shouldUpdateUpdatedAtWhenNumberIsUpdated() {
        var sample = createValidSample();
        var originalUpdatedAt = sample.getUpdatedAt();
        
        // 時間差を作るため少し待機
        try { Thread.sleep(10); } catch (InterruptedException e) {}
        
        sample.updateNumber(999);
        
        assertThat(sample.getUpdatedAt().getValue())
            .isAfter(originalUpdatedAt.getValue());
    }
    
    @Test
    void shouldReturnCorrectTextLength() {
        var longText = "a".repeat(80);
        var sample = new Sample(
            new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZ1234"),
            new SampleText(longText),
            new SampleNumber(100)
        );
        
        assertThat(sample.hasLongText()).isTrue();
        
        var shortText = "短い";
        sample.updateText(shortText);
        
        assertThat(sample.hasLongText()).isFalse();
    }
    
    @Test
    void shouldHandleNumberIncrementCorrectly() {
        var sample = createValidSample();
        var originalNumber = sample.getNum1().getValue();
        
        sample.incrementNumber(50);
        
        assertThat(sample.getNum1().getValue()).isEqualTo(originalNumber + 50);
    }
    
    @Test
    void shouldThrowExceptionWhenIncrementExceedsLimit() {
        var sample = new Sample(
            new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZ1234"),
            new SampleText("テスト"),
            new SampleNumber(9990)
        );
        
        assertThrows(IllegalArgumentException.class, 
            () -> sample.incrementNumber(20));
    }
    
    @Test
    void shouldImplementEqualsBasedOnId() {
        var id1 = new SampleId(1L);
        var id2 = new SampleId(1L);
        var id3 = new SampleId(2L);
        
        var sample1 = new Sample(id1, 
            new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZ1234"),
            new SampleText("テスト1"), new SampleNumber(100),
            CreatedAt.now(), UpdatedAt.now());
            
        var sample2 = new Sample(id2,
            new DisplayId("456ABCDEFGHIJKLMNOPQRSTUVWXYZ5678"),
            new SampleText("テスト2"), new SampleNumber(200),
            CreatedAt.now(), UpdatedAt.now());
            
        var sample3 = new Sample(id3,
            new DisplayId("789ABCDEFGHIJKLMNOPQRSTUVWXYZ9012"),
            new SampleText("テスト3"), new SampleNumber(300),
            CreatedAt.now(), UpdatedAt.now());
        
        assertThat(sample1).isEqualTo(sample2); // 同じID
        assertThat(sample1).isNotEqualTo(sample3); // 異なるID
    }
    
    private Sample createValidSample() {
        return new Sample(
            new DisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234"),
            new SampleText("テスト"),
            new SampleNumber(100)
        );
    }
}