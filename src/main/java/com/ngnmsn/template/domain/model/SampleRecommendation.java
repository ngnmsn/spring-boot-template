package com.ngnmsn.template.domain.model;

import java.util.Objects;

public final class SampleRecommendation {
    private final RecommendationType type;
    private final String message;
    
    public SampleRecommendation(RecommendationType type, String message) {
        this.type = Objects.requireNonNull(type);
        this.message = Objects.requireNonNull(message);
    }
    
    public RecommendationType getType() { return type; }
    public String getMessage() { return message; }
}