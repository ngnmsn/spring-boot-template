package com.ngnmsn.template.domain.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class SampleNumberTest {
    
    @Test
    void shouldCreateSampleNumberSuccessfully() {
        var sampleNumber = new SampleNumber(100);
        
        assertThat(sampleNumber.getValue()).isEqualTo(100);
    }
    
    @Test
    void shouldThrowExceptionWhenNumberIsNull() {
        assertThatThrownBy(() -> new SampleNumber(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("数値はnullにできません");
    }
    
    @Test
    void shouldThrowExceptionWhenNumberIsBelowMin() {
        assertThatThrownBy(() -> new SampleNumber(-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("数値は0以上9999以下で入力してください");
    }
    
    @Test
    void shouldThrowExceptionWhenNumberIsAboveMax() {
        assertThatThrownBy(() -> new SampleNumber(10000))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("数値は0以上9999以下で入力してください");
    }
    
    @Test
    void shouldReturnTrueForEvenNumber() {
        var evenNumber = new SampleNumber(2);
        
        assertThat(evenNumber.isEven()).isTrue();
        assertThat(evenNumber.isOdd()).isFalse();
    }
    
    @Test
    void shouldReturnTrueForOddNumber() {
        var oddNumber = new SampleNumber(3);
        
        assertThat(oddNumber.isOdd()).isTrue();
        assertThat(oddNumber.isEven()).isFalse();
    }
    
    @Test
    void shouldAddValueCorrectly() {
        var original = new SampleNumber(100);
        var added = original.add(50);
        
        assertThat(added.getValue()).isEqualTo(150);
        assertThat(original.getValue()).isEqualTo(100);
    }
    
    @Test
    void shouldBeEqualWhenValueIsSame() {
        var number1 = new SampleNumber(123);
        var number2 = new SampleNumber(123);
        
        assertThat(number1).isEqualTo(number2);
        assertThat(number1.hashCode()).isEqualTo(number2.hashCode());
    }
}