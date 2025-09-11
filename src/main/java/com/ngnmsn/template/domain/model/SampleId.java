package com.ngnmsn.template.domain.model;

import java.util.Objects;

public final class SampleId {
    private final Long value;
    
    public SampleId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("SampleIdは正の数値である必要があります");
        }
        this.value = value;
    }
    
    public static SampleId generate() {
        return new SampleId(System.currentTimeMillis());
    }
    
    public Long getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleId)) return false;
        SampleId sampleId = (SampleId) o;
        return Objects.equals(value, sampleId.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return "SampleId{" + value + '}';
    }
}