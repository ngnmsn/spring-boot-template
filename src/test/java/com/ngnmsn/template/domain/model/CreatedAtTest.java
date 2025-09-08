package com.ngnmsn.template.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.*;

class CreatedAtTest {
    
    @Test
    void shouldCreateCreatedAtSuccessfully() {
        var dateTime = LocalDateTime.of(2023, 1, 1, 12, 0, 0);
        var createdAt = new CreatedAt(dateTime);
        
        assertThat(createdAt.getValue()).isEqualTo(dateTime);
    }
    
    @Test
    void shouldThrowExceptionWhenDateTimeIsNull() {
        assertThatThrownBy(() -> new CreatedAt(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("作成日時はnullにできません");
    }
    
    @Test
    void shouldCreateCreatedAtWithCurrentTime() {
        var before = LocalDateTime.now();
        var createdAt = CreatedAt.now();
        var after = LocalDateTime.now();
        
        assertThat(createdAt.getValue()).isBetween(before, after);
    }
    
    @Test
    void shouldBeEqualWhenValueIsSame() {
        var dateTime = LocalDateTime.of(2023, 1, 1, 12, 0, 0);
        var createdAt1 = new CreatedAt(dateTime);
        var createdAt2 = new CreatedAt(dateTime);
        
        assertThat(createdAt1).isEqualTo(createdAt2);
        assertThat(createdAt1.hashCode()).isEqualTo(createdAt2.hashCode());
    }
}