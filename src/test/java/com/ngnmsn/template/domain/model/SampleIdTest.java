package com.ngnmsn.template.domain.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class SampleIdTest {
    
    @Test
    void shouldCreateSampleIdSuccessfully() {
        var sampleId = new SampleId(123L);
        
        assertThat(sampleId.getValue()).isEqualTo(123L);
    }
    
    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        assertThatThrownBy(() -> new SampleId(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("SampleIdは正の数値である必要があります");
    }
    
    @Test
    void shouldThrowExceptionWhenIdIsZero() {
        assertThatThrownBy(() -> new SampleId(0L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("SampleIdは正の数値である必要があります");
    }
    
    @Test
    void shouldThrowExceptionWhenIdIsNegative() {
        assertThatThrownBy(() -> new SampleId(-1L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("SampleIdは正の数値である必要があります");
    }
    
    @Test
    void shouldGenerateValidSampleId() {
        var sampleId = SampleId.generate();
        
        assertThat(sampleId.getValue()).isPositive();
    }
    
    @Test
    void shouldBeEqualWhenValueIsSame() {
        var sampleId1 = new SampleId(123L);
        var sampleId2 = new SampleId(123L);
        
        assertThat(sampleId1).isEqualTo(sampleId2);
        assertThat(sampleId1.hashCode()).isEqualTo(sampleId2.hashCode());
    }
    
    @Test
    void shouldNotBeEqualWhenValueIsDifferent() {
        var sampleId1 = new SampleId(123L);
        var sampleId2 = new SampleId(456L);
        
        assertThat(sampleId1).isNotEqualTo(sampleId2);
    }
}