package com.ngnmsn.template.presentation.response;

import com.ngnmsn.template.domain.model.SampleRecommendation;
import com.ngnmsn.template.domain.model.RecommendationType;

public class RecommendationResponse {
    private final String type;
    private final String message;
    private final String severity;
    
    public RecommendationResponse(String type, String message, String severity) {
        this.type = type;
        this.message = message;
        this.severity = severity;
    }
    
    public static RecommendationResponse from(SampleRecommendation recommendation) {
        return new RecommendationResponse(
            recommendation.getType().name(),
            recommendation.getMessage(),
            determineSeverity(recommendation.getType())
        );
    }
    
    private static String determineSeverity(RecommendationType type) {
        switch (type) {
            case TEXT_OPTIMIZATION:
                return "INFO";
            case NUMBER_REVIEW:
                return "WARN";
            case DATA_REVIEW:
                return "ERROR";
            default:
                return "INFO";
        }
    }
    
    public String getType() { return type; }
    public String getMessage() { return message; }
    public String getSeverity() { return severity; }
}