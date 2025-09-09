package com.ngnmsn.template.presentation.response;

import com.ngnmsn.template.application.response.SampleDetailResponse;
import com.ngnmsn.template.domain.model.sample.Sample;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SampleResponse {
    private final Long id;
    private final String displayId;
    private final String text1;
    private final Integer num1;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<RecommendationResponse> recommendations;
    
    public SampleResponse(Long id, String displayId, String text1, Integer num1,
                         LocalDateTime createdAt, LocalDateTime updatedAt,
                         List<RecommendationResponse> recommendations) {
        this.id = id;
        this.displayId = displayId;
        this.text1 = text1;
        this.num1 = num1;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.recommendations = recommendations != null ? 
            new ArrayList<>(recommendations) : new ArrayList<>();
    }
    
    public static SampleResponse from(Sample sample) {
        return new SampleResponse(
            sample.getId().getValue(),
            sample.getDisplayId().getValue(),
            sample.getText1().getValue(),
            sample.getNum1().getValue(),
            sample.getCreatedAt().getValue(),
            sample.getUpdatedAt().getValue(),
            Collections.emptyList()
        );
    }
    
    public static SampleResponse from(SampleDetailResponse response) {
        var recommendations = response.getRecommendations().stream()
            .map(RecommendationResponse::from)
            .collect(Collectors.toList());
            
        return new SampleResponse(
            response.getId(),
            response.getDisplayId(),
            response.getText1(),
            response.getNum1(),
            response.getCreatedAt(),
            response.getUpdatedAt(),
            recommendations
        );
    }
    
    public Long getId() { return id; }
    public String getDisplayId() { return displayId; }
    public String getText1() { return text1; }
    public Integer getNum1() { return num1; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<RecommendationResponse> getRecommendations() { 
        return new ArrayList<>(recommendations); 
    }
}