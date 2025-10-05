package com.ngnmsn.template.application.service;

import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.command.SampleUpdateCommand;
import com.ngnmsn.template.application.query.SampleSearchQuery;
import com.ngnmsn.template.application.response.SampleDetailResponse;
import com.ngnmsn.template.application.dto.SampleUpdateDto;
import com.ngnmsn.template.application.exception.SampleApplicationException;
import com.ngnmsn.template.application.exception.SampleNotFoundException;
import com.ngnmsn.template.application.exception.SampleValidationException;
import com.ngnmsn.template.domain.exception.SampleBusinessException;
import com.ngnmsn.template.domain.model.SampleId;
import com.ngnmsn.template.domain.model.SampleSearchCriteria;
import com.ngnmsn.template.domain.model.SampleSearchResults;
import com.ngnmsn.template.domain.model.SampleStatistics;
import com.ngnmsn.template.domain.model.sample.DisplayId;
import com.ngnmsn.template.domain.repository.SampleRepositoryPort;
import com.ngnmsn.template.domain.service.SampleDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class SampleApplicationService {
    private final SampleRepositoryPort sampleRepository;
    private final SampleDomainService sampleDomainService;
    
    public SampleApplicationService(
            SampleRepositoryPort sampleRepository,
            SampleDomainService sampleDomainService) {
        this.sampleRepository = Objects.requireNonNull(sampleRepository, 
            "SampleRepositoryPortは必須です");
        this.sampleDomainService = Objects.requireNonNull(sampleDomainService, 
            "SampleDomainServiceは必須です");
    }
    
    /**
     * サンプル詳細取得ユースケース
     */
    @Transactional(readOnly = true)
    public SampleDetailResponse findById(Long id) {
        var sampleId = new SampleId(id);
        var sample = sampleRepository.findById(sampleId)
            .orElseThrow(() -> new SampleNotFoundException("サンプルが見つかりません: " + id));
        
        // ドメインサービスを使用して推奨アクションを取得
        var recommendations = sampleDomainService.generateRecommendations(sample);
        
        return SampleDetailResponse.from(sample, recommendations);
    }
    
    /**
     * サンプル検索ユースケース
     */
    @Transactional(readOnly = true)
    public SampleSearchResults search(SampleSearchQuery query) {
        var criteria = new SampleSearchCriteria(
            query.getDisplayId() != null ? new DisplayId(query.getDisplayId()) : null,
            query.getText1(),
            query.getPage(),
            query.getMaxNumPerPage()
        );
        
        return sampleRepository.search(criteria);
    }
    
    /**
     * サンプル作成ユースケース
     */
    public SampleDetailResponse createSample(SampleCreateCommand command) {
        try {
            // ドメインサービスを使用して重複チェック付きで作成
            var sample = sampleDomainService.createSampleWithDuplicationCheck(
                command.getText1(), 
                command.getNum1()
            );
            
            // 永続化
            sampleRepository.save(sample);
            
            // レスポンス作成
            var recommendations = sampleDomainService.generateRecommendations(sample);
            return SampleDetailResponse.from(sample, recommendations);
            
        } catch (IllegalArgumentException e) {
            throw new SampleValidationException("入力値が不正です: " + e.getMessage(), e);
        } catch (SampleBusinessException e) {
            throw new SampleApplicationException(e.getMessage(), e);
        }
    }
    
    /**
     * サンプル更新ユースケース
     */
    public SampleDetailResponse updateSample(Long id, SampleUpdateCommand command) {
        var sampleId = new SampleId(id);
        var sample = sampleRepository.findById(sampleId)
            .orElseThrow(() -> new SampleNotFoundException("サンプルが見つかりません: " + id));
        
        try {
            // ドメインオブジェクトのビジネスロジックを使用
            if (command.getText1() != null) {
                sample.updateText(command.getText1());
            }
            
            if (command.getNum1() != null) {
                sample.updateNumber(command.getNum1());
            }
            
            // 永続化
            sampleRepository.save(sample);
            
            // レスポンス作成
            var recommendations = sampleDomainService.generateRecommendations(sample);
            return SampleDetailResponse.from(sample, recommendations);

        } catch (SampleBusinessException e) {
            throw new SampleApplicationException(e.getMessage(), e);
        }
    }

    /**
     * サンプル削除ユースケース
     */
    public void deleteSample(Long id) {
        var sampleId = new SampleId(id);
        var sample = sampleRepository.findById(sampleId)
            .orElseThrow(() -> new SampleNotFoundException("サンプルが見つかりません: " + id));

        try {
            // ドメインオブジェクトのビジネスロジックで削除可能かチェック
            if (!sample.canBeDeleted()) {
                throw new SampleBusinessException(
                    "このサンプルは削除できません。削除条件を満たしていません。");
            }

            sampleRepository.deleteById(sampleId);
        } catch (SampleBusinessException e) {
            throw new SampleApplicationException(e.getMessage(), e);
        }
    }
    
    /**
     * 一括削除ユースケース
     */
    public void bulkDeleteSamples(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("削除対象のIDが指定されていません");
        }
        
        var sampleIds = ids.stream()
            .map(SampleId::new)
            .collect(Collectors.toList());
        
        // ドメインサービスで一括削除の妥当性をチェック
        sampleDomainService.validateBulkDeletion(sampleIds);
        
        // 全て削除可能であることが確認できたら実行
        sampleIds.forEach(sampleRepository::deleteById);
    }
    
    /**
     * 統計情報取得ユースケース
     */
    @Transactional(readOnly = true)
    public SampleStatistics getStatistics(SampleSearchQuery query) {
        var criteria = new SampleSearchCriteria(
            query.getDisplayId() != null ? new DisplayId(query.getDisplayId()) : null,
            query.getText1(),
            1, // 統計用は全件取得
            Integer.MAX_VALUE
        );
        
        return sampleDomainService.calculateStatistics(criteria);
    }
    
    /**
     * 数値インクリメントユースケース
     */
    public SampleDetailResponse incrementSampleNumber(Long id, int increment) {
        var sampleId = new SampleId(id);
        var sample = sampleRepository.findById(sampleId)
            .orElseThrow(() -> new SampleNotFoundException("サンプルが見つかりません: " + id));
        
        try {
            // ドメインオブジェクトのビジネスロジックを使用
            sample.incrementNumber(increment);
            
            // 永続化
            sampleRepository.save(sample);
            
            // レスポンス作成
            var recommendations = sampleDomainService.generateRecommendations(sample);
            return SampleDetailResponse.from(sample, recommendations);
            
        } catch (IllegalArgumentException e) {
            throw new SampleValidationException("インクリメント値が不正です: " + e.getMessage(), e);
        }
    }
    
    /**
     * SampleDetailResponseからSampleUpdateDtoへの変換
     * プレゼンテーション層でのApplication Responseの直接アクセスを回避するためのメソッド
     */
    public SampleUpdateDto convertToUpdateDto(SampleDetailResponse response) {
        return new SampleUpdateDto(response.getText1(), response.getNum1());
    }
    
    /**
     * SampleDetailResponseからIDを取得
     * プレゼンテーション層でのApplication Responseの直接アクセスを回避するためのメソッド
     */
    public Long extractId(SampleDetailResponse response) {
        return response.getId();
    }
}