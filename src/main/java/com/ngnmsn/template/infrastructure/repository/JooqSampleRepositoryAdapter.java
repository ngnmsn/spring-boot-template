package com.ngnmsn.template.infrastructure.repository.jooq.sample;

import static com.ngnmsn.template.Tables.SAMPLES;
import static org.jooq.impl.DSL.count;

import com.ngnmsn.template.domain.model.SampleSearchCriteria;
import com.ngnmsn.template.domain.model.SampleSearchResults;
import com.ngnmsn.template.domain.model.sample.DisplayId;
import com.ngnmsn.template.domain.model.sample.Sample;
import com.ngnmsn.template.domain.model.SampleId;
import com.ngnmsn.template.domain.model.SampleText;
import com.ngnmsn.template.domain.model.SampleNumber;
import com.ngnmsn.template.domain.model.CreatedAt;
import com.ngnmsn.template.domain.model.UpdatedAt;
import com.ngnmsn.template.domain.repository.SampleRepositoryPort;
import com.ngnmsn.template.tables.records.SamplesRecord;
import java.util.List;
import java.util.Optional;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;
import org.springframework.stereotype.Repository;

@Repository
public class JooqSampleRepositoryAdapter implements SampleRepositoryPort {

    private final DSLContext dsl;

    public JooqSampleRepositoryAdapter(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Optional<Sample> findById(SampleId id) {
        SamplesRecord record = dsl.selectFrom(SAMPLES)
            .where(SAMPLES.ID.eq(id.getValue()))
            .fetchOne();
        
        return record != null ? Optional.of(toDomainModel(record)) : Optional.empty();
    }

    @Override
    public Optional<Sample> findByDisplayId(DisplayId displayId) {
        SamplesRecord record = dsl.selectFrom(SAMPLES)
            .where(SAMPLES.DISPLAY_ID.eq(displayId.getValue()))
            .fetchOne();
        
        return record != null ? Optional.of(toDomainModel(record)) : Optional.empty();
    }

    @Override
    public SampleSearchResults search(SampleSearchCriteria criteria) {
        Condition condition = buildSearchCondition(criteria);
        
        // Get total count
        int totalCount = dsl.selectCount()
            .from(SAMPLES)
            .where(condition)
            .fetchOne(0, int.class);
        
        // Get paginated results
        Result<SamplesRecord> records = dsl.selectFrom(SAMPLES)
            .where(condition)
            .limit(criteria.getMaxNumPerPage())
            .offset((criteria.getPage() - 1) * criteria.getMaxNumPerPage())
            .fetch();
        
        List<Sample> samples = records.stream()
            .map(this::toDomainModel)
            .toList();
        
        return new SampleSearchResults(samples, totalCount, criteria.getPage(), criteria.getMaxNumPerPage());
    }

    @Override
    public void save(Sample sample) {
        if (sample.getId() != null && existsById(sample.getId())) {
            updateSample(sample);
        } else {
            insertSample(sample);
        }
    }

    @Override
    public void deleteById(SampleId id) {
        dsl.deleteFrom(SAMPLES)
            .where(SAMPLES.ID.eq(id.getValue()))
            .execute();
    }

    @Override
    public boolean existsByDisplayId(DisplayId displayId) {
        return dsl.fetchExists(
            dsl.selectOne()
                .from(SAMPLES)
                .where(SAMPLES.DISPLAY_ID.eq(displayId.getValue()))
        );
    }

    private boolean existsById(SampleId id) {
        return dsl.fetchExists(
            dsl.selectOne()
                .from(SAMPLES)
                .where(SAMPLES.ID.eq(id.getValue()))
        );
    }

    private void insertSample(Sample sample) {
        dsl.insertInto(SAMPLES)
            .set(SAMPLES.DISPLAY_ID, sample.getDisplayId().getValue())
            .set(SAMPLES.TEXT1, sample.getText1().getValue())
            .set(SAMPLES.NUM1, sample.getNum1().getValue())
            .execute();
    }

    private void updateSample(Sample sample) {
        dsl.update(SAMPLES)
            .set(SAMPLES.DISPLAY_ID, sample.getDisplayId().getValue())
            .set(SAMPLES.TEXT1, sample.getText1().getValue())
            .set(SAMPLES.NUM1, sample.getNum1().getValue())
            .where(SAMPLES.ID.eq(sample.getId().getValue()))
            .execute();
    }

    private Sample toDomainModel(SamplesRecord record) {
        SampleId id = record.getId() != null ? new SampleId(record.getId()) : null;
        DisplayId displayId = new DisplayId(record.getDisplayId());
        SampleText text1 = new SampleText(record.getText1());
        SampleNumber num1 = new SampleNumber(record.getNum1());
        CreatedAt createdAt = record.getCreatedAt() != null ? new CreatedAt(record.getCreatedAt()) : CreatedAt.now();
        UpdatedAt updatedAt = record.getUpdatedAt() != null ? new UpdatedAt(record.getUpdatedAt()) : UpdatedAt.now();
        
        return new Sample(id, displayId, text1, num1, createdAt, updatedAt);
    }

    private Condition buildSearchCondition(SampleSearchCriteria criteria) {
        Condition condition = DSL.noCondition();
        
        if (criteria.getDisplayId() != null) {
            condition = condition.and(SAMPLES.DISPLAY_ID.like("%" + criteria.getDisplayId().getValue() + "%"));
        }
        
        if (criteria.getText1() != null && !criteria.getText1().trim().isEmpty()) {
            condition = condition.and(SAMPLES.TEXT1.like("%" + criteria.getText1() + "%"));
        }
        
        return condition;
    }
}