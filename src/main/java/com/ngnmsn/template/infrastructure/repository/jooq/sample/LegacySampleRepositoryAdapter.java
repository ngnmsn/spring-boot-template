package com.ngnmsn.template.infrastructure.repository.jooq.sample;

import com.ngnmsn.template.application.port.SampleRepositoryPort;
import com.ngnmsn.template.domain.model.sample.SampleResult;
import com.ngnmsn.template.domain.model.sample.SampleResults;
import com.ngnmsn.template.infrastructure.repository.jooq.sample.SampleRepository;
import org.jooq.types.ULong;
import org.springframework.stereotype.Component;

/**
 * Legacy sample repository adapter
 * Bridges the gap between the new port interface and the existing repository implementation
 */
@Component
public class LegacySampleRepositoryAdapter implements SampleRepositoryPort {

    private final SampleRepository legacySampleRepository;

    public LegacySampleRepositoryAdapter(SampleRepository legacySampleRepository) {
        this.legacySampleRepository = legacySampleRepository;
    }

    @Override
    public SampleResults search(String displayId, String text1, int page, int maxNumPerPage) {
        return legacySampleRepository.search(displayId, text1, page, maxNumPerPage);
    }

    @Override
    public SampleResult findByDisplayId(String displayId) {
        return legacySampleRepository.findByDisplayId(displayId);
    }

    @Override
    public void insert(String displayId, String text1, Integer num1) {
        legacySampleRepository.insert(displayId, text1, num1);
    }

    @Override
    public void update(ULong id, String text1, Integer num1) {
        legacySampleRepository.update(id, text1, num1);
    }

    @Override
    public void delete(ULong id) {
        legacySampleRepository.delete(id);
    }
}