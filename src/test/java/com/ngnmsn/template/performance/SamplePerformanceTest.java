package com.ngnmsn.template.performance;

import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.query.SampleSearchQuery;
import com.ngnmsn.template.application.service.SampleApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SamplePerformanceTest {
    
    @Autowired
    private SampleApplicationService sampleApplicationService;
    
    @Test
    void measureSampleCreationPerformance() {
        var iterations = 100;
        var startTime = System.currentTimeMillis();
        
        for (int i = 0; i < iterations; i++) {
            var command = new SampleCreateCommand("パフォーマンステスト" + i, i);
            sampleApplicationService.createSample(command);
        }
        
        var endTime = System.currentTimeMillis();
        var totalTime = endTime - startTime;
        var averageTime = totalTime / (double) iterations;
        
        System.out.println(String.format("サンプル作成 - 合計: %d ms, 平均: %.2f ms/件", totalTime, averageTime));
        
        // パフォーマンス基準をチェック
        assertThat(averageTime).isLessThan(50.0); // 1件あたり50ms以下
    }
    
    @Test
    void measureSampleSearchPerformance() {
        // 事前データ準備
        prepareTestData(1000);
        
        var startTime = System.currentTimeMillis();
        
        var query = new SampleSearchQuery(null, "テスト", 1, 20);
        var results = sampleApplicationService.search(query);
        
        var endTime = System.currentTimeMillis();
        var searchTime = endTime - startTime;
        
        System.out.println(String.format("サンプル検索 - 検索時間: %d ms, 結果件数: %d件", 
            searchTime, results.getTotalCount()));
        
        // パフォーマンス基準をチェック
        assertThat(searchTime).isLessThan(200); // 200ms以下
    }
    
    @Test
    void measureApplicationStartupTime() {
        System.out.println("アプリケーション起動パフォーマンス測定");
        
        // Spring起動時間の測定（概算）
        var startTime = System.currentTimeMillis();
        
        // 簡単な操作でSpring contextが完全に起動していることを確認
        var query = new SampleSearchQuery(null, null, 1, 1);
        sampleApplicationService.search(query);
        
        var endTime = System.currentTimeMillis();
        var contextReadyTime = endTime - startTime;
        
        System.out.println(String.format("Context準備時間: %d ms", contextReadyTime));
        
        // 起動時間の基準（テスト環境では緩め）
        assertThat(contextReadyTime).isLessThan(5000); // 5秒以下
    }
    
    @Test
    void measureSampleListPerformance() {
        // 事前データ準備（大量データ）
        prepareTestData(1000);
        
        var startTime = System.currentTimeMillis();
        
        // 全件検索
        var query = new SampleSearchQuery(null, null, 1, 1000);
        var results = sampleApplicationService.search(query);
        
        var endTime = System.currentTimeMillis();
        var listTime = endTime - startTime;
        
        System.out.println(String.format("サンプル一覧取得（1000件） - 取得時間: %d ms", listTime));
        
        // パフォーマンス基準をチェック
        assertThat(listTime).isLessThan(500); // 500ms以下
        assertThat(results.getTotalCount()).isGreaterThanOrEqualTo(1000);
    }
    
    private void prepareTestData(int count) {
        for (int i = 0; i < count; i++) {
            var command = new SampleCreateCommand("テストデータ" + i, i);
            sampleApplicationService.createSample(command);
        }
        
        System.out.println(String.format("テストデータ %d件を準備しました", count));
    }
}