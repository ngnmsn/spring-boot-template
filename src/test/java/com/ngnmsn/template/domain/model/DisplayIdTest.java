package com.ngnmsn.template.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class DisplayIdTest {
    
    @Test
    void shouldCreateDisplayIdWithValidFormat() {
        var validId = "123ABCDEFGHIJKLMNOPQRSTUVWXYZABCD";
        
        var displayId = new DisplayId(validId);
        
        assertThat(displayId.getValue()).isEqualTo(validId);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "",                                    // 空文字
        "12ABCDEFGHIJKLMNOPQRSTUVWXYZ1234",    // 数字2桁
        "1234ABCDEFGHIJKLMNOPQRSTUVWXYZ1234",  // 数字4桁
        "123abcdefghijklmnopqrstuvwxyz1234",   // 小文字
        "123ABCDEFGHIJKLMNOPQRSTUVWXYZ123",    // 英字29桁
        "123ABCDEFGHIJKLMNOPQRSTUVWXYZ12345",  // 英字31桁
        "123ABCDEFGHIJKLMNOPQRSTUVWXYZあ12",  // 日本語文字
        "123ABCDEFGHIJKLMNOPQRSTUVWXYZ123-",   // 記号
    })
    void shouldThrowExceptionForInvalidFormat(String invalidId) {
        assertThrows(IllegalArgumentException.class, 
            () -> new DisplayId(invalidId));
    }
    
    @Test
    void shouldThrowExceptionForNullValue() {
        assertThrows(IllegalArgumentException.class, 
            () -> new DisplayId(null));
    }
    
    @Test
    void shouldImplementEqualsCorrectly() {
        var id1 = new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZABCD");
        var id2 = new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZABCD");
        var id3 = new DisplayId("456ABCDEFGHIJKLMNOPQRSTUVWXYZEFGH");
        
        assertThat(id1).isEqualTo(id2);
        assertThat(id1).isNotEqualTo(id3);
        assertThat(id1).isNotEqualTo(null);
        assertThat(id1).isNotEqualTo("string");
    }
    
    @Test
    void shouldImplementHashCodeCorrectly() {
        var id1 = new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZABCD");
        var id2 = new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZABCD");
        
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
    }
    
    @Test
    void shouldReturnValueAsString() {
        var value = "123ABCDEFGHIJKLMNOPQRSTUVWXYZABCD";
        var displayId = new DisplayId(value);
        
        assertThat(displayId.toString()).isEqualTo(value);
    }
}