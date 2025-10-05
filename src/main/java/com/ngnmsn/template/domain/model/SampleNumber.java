package com.ngnmsn.template.domain.model;

import java.util.Objects;

public final class SampleNumber {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 9999;
    private final Integer value;
    
    public SampleNumber(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("数値はnullにできません");
        }
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException(
                "数値は" + MIN_VALUE + "以上" + MAX_VALUE + "以下で入力してください");
        }
        this.value = value;
    }
    
    public Integer getValue() {
        return value;
    }
    
    public boolean isEven() {
        return value % 2 == 0;
    }
    
    public boolean isOdd() {
        return !isEven();
    }
    
    public SampleNumber add(int addValue) {
        return new SampleNumber(this.value + addValue);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleNumber)) return false;
        SampleNumber that = (SampleNumber) o;
        return Objects.equals(value, that.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}