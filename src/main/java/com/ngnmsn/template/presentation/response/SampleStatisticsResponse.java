package com.ngnmsn.template.presentation.response;

public class SampleStatisticsResponse {
    private final long totalCount;
    private final long averageNum1;
    private final int minNum1;
    private final int maxNum1;
    
    public SampleStatisticsResponse(long totalCount, long averageNum1, int minNum1, int maxNum1) {
        this.totalCount = totalCount;
        this.averageNum1 = averageNum1;
        this.minNum1 = minNum1;
        this.maxNum1 = maxNum1;
    }
    
    public static SampleStatisticsResponse from(Object statistics) {
        return new SampleStatisticsResponse(0, 0, 0, 0);
    }
    
    public long getTotalCount() { return totalCount; }
    public long getAverageNum1() { return averageNum1; }
    public int getMinNum1() { return minNum1; }
    public int getMaxNum1() { return maxNum1; }
}