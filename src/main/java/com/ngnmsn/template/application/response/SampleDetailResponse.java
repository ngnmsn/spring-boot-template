package com.ngnmsn.template.application.response;

import com.ngnmsn.template.domain.model.SampleRecommendation;
import com.ngnmsn.template.domain.model.RecommendationType;
import com.ngnmsn.template.domain.model.sample.Sample;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SampleDetailResponse {
    private final Long id;
    private final String displayId;
    private final String text1;
    private final Integer num1;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<SampleRecommendation> recommendations;
    
    public SampleDetailResponse(Long id, String displayId, String text1, Integer num1,
                               LocalDateTime createdAt, LocalDateTime updatedAt,
                               List<SampleRecommendation> recommendations) {
        this.id = id;
        this.displayId = displayId;
        this.text1 = text1;
        this.num1 = num1;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.recommendations = recommendations != null ? 
            new ArrayList<>(recommendations) : new ArrayList<>();
    }
    
    public static SampleDetailResponse from(Sample sample, List<SampleRecommendation> recommendations) {
        return new SampleDetailResponse(
            sample.getId().getValue(),
            sample.getDisplayId().getValue(),
            sample.getText1().getValue(),
            sample.getNum1().getValue(),
            sample.getCreatedAt().getValue(),
            sample.getUpdatedAt().getValue(),
            recommendations
        );
    }
    
    public static SampleDetailResponse from(Sample sample) {
        return from(sample, Collections.emptyList());
    }
    
    // getterメソッド
    public Long getId() { return id; }
    public String getDisplayId() { return displayId; }
    public String getText1() { return text1; }
    public Integer getNum1() { return num1; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<SampleRecommendation> getRecommendations() { 
        return new ArrayList<>(recommendations); 
    }
    
    // ビジネスメソッド
    public boolean hasRecommendations() {
        return !recommendations.isEmpty();
    }
    
    public boolean hasRecommendationType(RecommendationType type) {
        return recommendations.stream()
            .anyMatch(rec -> rec.getType() == type);
    }
}