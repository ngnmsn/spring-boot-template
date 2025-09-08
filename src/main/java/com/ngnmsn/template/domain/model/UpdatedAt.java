package com.ngnmsn.template.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class UpdatedAt {
    private final LocalDateTime value;
    
    public UpdatedAt(LocalDateTime value) {
        if (value == null) {
            throw new IllegalArgumentException("更新日時はnullにできません");
        }
        this.value = value;
    }
    
    public static UpdatedAt now() {
        return new UpdatedAt(LocalDateTime.now());
    }
    
    public LocalDateTime getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdatedAt)) return false;
        UpdatedAt updatedAt = (UpdatedAt) o;
        return Objects.equals(value, updatedAt.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return "UpdatedAt{" + value + '}';
    }
}