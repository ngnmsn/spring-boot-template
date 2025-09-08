package com.ngnmsn.template.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class CreatedAt {
    private final LocalDateTime value;
    
    public CreatedAt(LocalDateTime value) {
        if (value == null) {
            throw new IllegalArgumentException("作成日時はnullにできません");
        }
        this.value = value;
    }
    
    public static CreatedAt now() {
        return new CreatedAt(LocalDateTime.now());
    }
    
    public LocalDateTime getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreatedAt)) return false;
        CreatedAt createdAt = (CreatedAt) o;
        return Objects.equals(value, createdAt.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return "CreatedAt{" + value + '}';
    }
}