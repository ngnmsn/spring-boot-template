package com.ngnmsn.template.domain.model;

public final class SampleStatistics {
    private final int totalCount;
    private final double averageNumber;
    private final long longTextCount;
    private final long evenNumberCount;
    
    public SampleStatistics(int totalCount, double averageNumber, 
                           long longTextCount, long evenNumberCount) {
        this.totalCount = totalCount;
        this.averageNumber = averageNumber;
        this.longTextCount = longTextCount;
        this.evenNumberCount = evenNumberCount;
    }
    
    public static SampleStatistics empty() {
        return new SampleStatistics(0, 0.0, 0, 0);
    }
    
    // getterメソッド
    public int getTotalCount() { return totalCount; }
    public double getAverageNumber() { return averageNumber; }
    public long getLongTextCount() { return longTextCount; }
    public long getEvenNumberCount() { return evenNumberCount; }
    
    // ビジネスメソッド
    public double getLongTextPercentage() {
        return totalCount > 0 ? (double) longTextCount / totalCount * 100 : 0;
    }
    
    public double getEvenNumberPercentage() {
        return totalCount > 0 ? (double) evenNumberCount / totalCount * 100 : 0;
    }
}