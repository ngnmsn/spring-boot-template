package com.ngnmsn.template.domain.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class SampleTextTest {
    
    @Test
    void shouldCreateSampleTextSuccessfully() {
        var sampleText = new SampleText("テストテキスト");
        
        assertThat(sampleText.getValue()).isEqualTo("テストテキスト");
        assertThat(sampleText.isNotEmpty()).isTrue();
    }
    
    @Test
    void shouldThrowExceptionWhenTextIsNull() {
        assertThatThrownBy(() -> new SampleText(null))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    void shouldThrowExceptionWhenTextIsTooLong() {
        var longText = "a".repeat(101);
        
        assertThatThrownBy(() -> new SampleText(longText))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    void shouldReturnTrueWhenTextIsEmpty() {
        var emptyText = new SampleText("");
        
        assertThat(emptyText.isEmpty()).isTrue();
    }
}