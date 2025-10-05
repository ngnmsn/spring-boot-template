package com.ngnmsn.template.domain.model;

import java.util.Objects;

public final class SampleText {
    private static final int MAX_LENGTH = 100;
    private final String value;
    
    public SampleText(String value) {
        if (value == null) {
            throw new IllegalArgumentException("テキストはnullにできません");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                "テキストは" + MAX_LENGTH + "文字以内で入力してください（現在:" + value.length() + "文字）");
        }
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public boolean isEmpty() {
        return value.trim().isEmpty();
    }
    
    public boolean isNotEmpty() {
        return !isEmpty();
    }
    
    public int length() {
        return value.length();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleText)) return false;
        SampleText that = (SampleText) o;
        return Objects.equals(value, that.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return value;
    }
}