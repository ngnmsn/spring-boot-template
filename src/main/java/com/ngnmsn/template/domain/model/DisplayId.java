package com.ngnmsn.template.domain.model;

import java.util.Objects;

public final class DisplayId {
    private static final String FORMAT_PATTERN = "^[0-9]{3}[A-Z]{30}$";
    private final String value;
    
    public DisplayId(String value) {
        if (value == null || !value.matches(FORMAT_PATTERN)) {
            throw new IllegalArgumentException(
                "表示IDの形式が不正です。3桁数字+30桁英大文字の形式で入力してください: " + value);
        }
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DisplayId)) return false;
        DisplayId displayId = (DisplayId) o;
        return Objects.equals(value, displayId.value);
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