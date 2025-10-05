package com.ngnmsn.template.domain.service;

import com.ngnmsn.template.domain.exception.SampleBusinessException;
import com.ngnmsn.template.domain.model.SampleSearchCriteria;
import com.ngnmsn.template.domain.model.SampleStatistics;
import com.ngnmsn.template.domain.model.SampleText;
import com.ngnmsn.template.domain.model.SampleNumber;
import com.ngnmsn.template.domain.model.SampleRecommendation;
import com.ngnmsn.template.domain.model.RecommendationType;
import com.ngnmsn.template.domain.model.sample.DisplayId;
import com.ngnmsn.template.domain.model.sample.Sample;
import com.ngnmsn.template.domain.model.SampleId;
import com.ngnmsn.template.domain.repository.SampleRepositoryPort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class SampleDomainService {
    private final SampleRepositoryPort sampleRepository;
    
    public SampleDomainService(SampleRepositoryPort sampleRepository) {
        this.sampleRepository = Objects.requireNonNull(sampleRepository, 
            "SampleRepositoryPortは必須です");
    }
    
    /**
     * 一意な表示IDを生成する
     * ビジネスルール：表示IDは重複してはならない
     */
    public DisplayId generateUniqueDisplayId() {
        DisplayId displayId;
        int attempts = 0;
        
        do {
            displayId = generateDisplayId();
            attempts++;
            
            if (attempts > 10) {
                throw new SampleBusinessException(
                    "表示IDの生成に失敗しました。システム管理者に連絡してください");
            }
        } while (sampleRepository.existsByDisplayId(displayId));
        
        return displayId;
    }
    
    /**
     * 重複チェック付きでサンプルを作成する
     * ビジネスルール：同じテキスト内容のサンプルは作成できない
     */
    public Sample createSampleWithDuplicationCheck(String text1, Integer num1) {
        var sampleText = new SampleText(text1);
        var sampleNumber = new SampleNumber(num1);
        
        // 重複チェック（例：同じテキストが既に存在する場合）
        if (isDuplicateText(sampleText)) {
            throw new SampleBusinessException(
                "同じテキスト内容のサンプルが既に存在します: " + text1);
        }
        
        var displayId = generateUniqueDisplayId();
        return new Sample(displayId, sampleText, sampleNumber);
    }
    
    /**
     * 一括削除の妥当性チェック
     * ビジネスルール：削除不可能なサンプルが含まれている場合は全体を削除しない
     */
    public void validateBulkDeletion(List<SampleId> sampleIds) {
        var samples = sampleIds.stream()
            .map(id -> sampleRepository.findById(id)
                .orElseThrow(() -> new SampleBusinessException("サンプルが見つかりません: " + id)))
            .collect(Collectors.toList());
        
        var undeletableSamples = samples.stream()
            .filter(sample -> !sample.canBeDeleted())
            .collect(Collectors.toList());
        
        if (!undeletableSamples.isEmpty()) {
            var undeletableIds = undeletableSamples.stream()
                .map(sample -> sample.getId().getValue().toString())
                .collect(Collectors.joining(", "));
            
            throw new SampleBusinessException(
                "削除できないサンプルが含まれています: " + undeletableIds);
        }
    }
    
    /**
     * サンプルの統計情報を計算
     * ビジネスルール：統計計算は複雑なドメインロジック
     */
    public SampleStatistics calculateStatistics(SampleSearchCriteria criteria) {
        var searchResults = sampleRepository.search(criteria);
        var samples = searchResults.getResults();
        
        if (samples.isEmpty()) {
            return SampleStatistics.empty();
        }
        
        // 統計計算
        var totalCount = samples.size();
        var averageNumber = samples.stream()
            .mapToInt(sample -> sample.getNum1().getValue())
            .average()
            .orElse(0.0);
        
        var longTextCount = samples.stream()
            .mapToLong(sample -> sample.hasLongText() ? 1 : 0)
            .sum();
        
        var evenNumberCount = samples.stream()
            .mapToLong(sample -> sample.hasEvenNumber() ? 1 : 0)
            .sum();
        
        return new SampleStatistics(
            totalCount,
            averageNumber,
            longTextCount,
            evenNumberCount
        );
    }
    
    /**
     * ビジネスルールに基づく推奨アクション
     * 複雑な判定ロジックをドメインサービスに集約
     */
    public List<SampleRecommendation> generateRecommendations(Sample sample) {
        var recommendations = new ArrayList<SampleRecommendation>();
        
        // テキストが長い場合の推奨
        if (sample.hasLongText()) {
            recommendations.add(new SampleRecommendation(
                RecommendationType.TEXT_OPTIMIZATION,
                "テキストが長すぎます。短縮を検討してください。"
            ));
        }
        
        // 数値が大きい場合の推奨
        if (sample.getNum1().getValue() > 5000) {
            recommendations.add(new SampleRecommendation(
                RecommendationType.NUMBER_REVIEW,
                "数値が大きすぎる可能性があります。確認してください。"
            ));
        }
        
        // 古いデータの場合の推奨
        var sixMonthsAgo = LocalDateTime.now().minusMonths(6);
        if (sample.getCreatedAt().getValue().isBefore(sixMonthsAgo)) {
            recommendations.add(new SampleRecommendation(
                RecommendationType.DATA_REVIEW,
                "古いデータです。更新または削除を検討してください。"
            ));
        }
        
        return recommendations;
    }
    
    // プライベートメソッド
    private DisplayId generateDisplayId() {
        var sequence = String.format("%03d", generateSequenceNumber());
        var randomPart = generateRandomAlphabetic(29);
        return new DisplayId(sequence + randomPart);
    }
    
    private int generateSequenceNumber() {
        return ThreadLocalRandom.current().nextInt(1, 1000);
    }
    
    private String generateRandomAlphabetic(int length) {
        return ThreadLocalRandom.current()
            .ints(length, 'A', 'Z' + 1)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
    
    private boolean isDuplicateText(SampleText text) {
        // 実際の重複チェックロジック
        var criteria = new SampleSearchCriteria(null, text.getValue(), 1, 1);
        var results = sampleRepository.search(criteria);
        return !results.getResults().isEmpty();
    }
}