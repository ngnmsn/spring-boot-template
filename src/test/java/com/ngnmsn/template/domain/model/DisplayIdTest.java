package com.ngnmsn.template.domain.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class DisplayIdTest {
    
    @Test
    void shouldCreateDisplayIdSuccessfully() {
        var displayId = new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZABCD");
        
        assertThat(displayId.getValue()).isEqualTo("123ABCDEFGHIJKLMNOPQRSTUVWXYZABCD");
    }
    
    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        assertThatThrownBy(() -> new DisplayId(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("表示IDの形式が不正です");
    }
    
    @Test
    void shouldThrowExceptionWhenFormatIsInvalid() {
        assertThatThrownBy(() -> new DisplayId("invalid"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("表示IDの形式が不正です");
    }
    
    @Test
    void shouldThrowExceptionWhenNumericPartIsNot3Digits() {
        assertThatThrownBy(() -> new DisplayId("12ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("表示IDの形式が不正です");
    }
    
    @Test
    void shouldThrowExceptionWhenAlphaPartIsNot30Characters() {
        assertThatThrownBy(() -> new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZABC"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("表示IDの形式が不正です");
    }
    
    @Test
    void shouldThrowExceptionWhenContainsLowercase() {
        assertThatThrownBy(() -> new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZabcd"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("表示IDの形式が不正です");
    }
    
    @Test
    void shouldBeEqualWhenValueIsSame() {
        var displayId1 = new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZABCD");
        var displayId2 = new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZABCD");
        
        assertThat(displayId1).isEqualTo(displayId2);
        assertThat(displayId1.hashCode()).isEqualTo(displayId2.hashCode());
    }
}