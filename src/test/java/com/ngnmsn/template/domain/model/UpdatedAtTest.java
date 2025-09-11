package com.ngnmsn.template.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.*;

class UpdatedAtTest {
    
    @Test
    void shouldCreateUpdatedAtSuccessfully() {
        var dateTime = LocalDateTime.of(2023, 1, 1, 12, 0, 0);
        var updatedAt = new UpdatedAt(dateTime);
        
        assertThat(updatedAt.getValue()).isEqualTo(dateTime);
    }
    
    @Test
    void shouldThrowExceptionWhenDateTimeIsNull() {
        assertThatThrownBy(() -> new UpdatedAt(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("更新日時はnullにできません");
    }
    
    @Test
    void shouldCreateUpdatedAtWithCurrentTime() {
        var before = LocalDateTime.now();
        var updatedAt = UpdatedAt.now();
        var after = LocalDateTime.now();
        
        assertThat(updatedAt.getValue()).isBetween(before, after);
    }
    
    @Test
    void shouldBeEqualWhenValueIsSame() {
        var dateTime = LocalDateTime.of(2023, 1, 1, 12, 0, 0);
        var updatedAt1 = new UpdatedAt(dateTime);
        var updatedAt2 = new UpdatedAt(dateTime);
        
        assertThat(updatedAt1).isEqualTo(updatedAt2);
        assertThat(updatedAt1.hashCode()).isEqualTo(updatedAt2.hashCode());
    }
}