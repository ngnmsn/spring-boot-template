package com.ngnmsn.template.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SampleNumberTest {
    
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5000, 9999})
    void shouldCreateSampleNumberWithValidValues(int validValue) {
        var sampleNumber = new SampleNumber(validValue);
        
        assertThat(sampleNumber.getValue()).isEqualTo(validValue);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-1, -100, 10000, 99999})
    void shouldThrowExceptionForInvalidValues(int invalidValue) {
        assertThrows(IllegalArgumentException.class, 
            () -> new SampleNumber(invalidValue));
    }
    
    @Test
    void shouldThrowExceptionForNullValue() {
        assertThrows(IllegalArgumentException.class, 
            () -> new SampleNumber(null));
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, 2, 4, 100, 9998})
    void shouldReturnTrueForEvenNumbers(int evenValue) {
        var sampleNumber = new SampleNumber(evenValue);
        
        assertThat(sampleNumber.isEven()).isTrue();
        assertThat(sampleNumber.isOdd()).isFalse();
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 99, 9999})
    void shouldReturnTrueForOddNumbers(int oddValue) {
        var sampleNumber = new SampleNumber(oddValue);
        
        assertThat(sampleNumber.isOdd()).isTrue();
        assertThat(sampleNumber.isEven()).isFalse();
    }
    
    @Test
    void shouldAddValueCorrectly() {
        var original = new SampleNumber(100);
        
        var result = original.add(50);
        
        assertThat(result.getValue()).isEqualTo(150);
        assertThat(original.getValue()).isEqualTo(100); // 不変性確認
    }
    
    @Test
    void shouldThrowExceptionWhenAdditionExceedsMaxValue() {
        var maxNumber = new SampleNumber(9999);
        
        assertThrows(IllegalArgumentException.class, 
            () -> maxNumber.add(1));
    }
    
    @Test
    void shouldThrowExceptionWhenAdditionGoesBelowMinValue() {
        var minNumber = new SampleNumber(0);
        
        assertThrows(IllegalArgumentException.class, 
            () -> minNumber.add(-1));
    }
}