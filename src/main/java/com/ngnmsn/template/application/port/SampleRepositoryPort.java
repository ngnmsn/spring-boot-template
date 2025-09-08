package com.ngnmsn.template.application.port;

import com.ngnmsn.template.domain.model.sample.SampleResult;
import com.ngnmsn.template.domain.model.sample.SampleResults;
import org.jooq.types.ULong;

/**
 * Sample repository port (legacy-compatible interface)
 * Maintains compatibility with existing service layer while introducing dependency inversion
 */
public interface SampleRepositoryPort {

    /**
     * Search samples with pagination
     *
     * @param displayId display ID filter
     * @param text1 text filter
     * @param page page number
     * @param maxNumPerPage max results per page
     * @return search results
     */
    SampleResults search(String displayId, String text1, int page, int maxNumPerPage);

    /**
     * Find sample by display ID
     *
     * @param displayId display ID
     * @return sample result
     */
    SampleResult findByDisplayId(String displayId);

    /**
     * Insert new sample
     *
     * @param displayId display ID
     * @param text1 text value
     * @param num1 number value
     */
    void insert(String displayId, String text1, Integer num1);

    /**
     * Update existing sample
     *
     * @param id sample ID
     * @param text1 text value
     * @param num1 number value
     */
    void update(ULong id, String text1, Integer num1);

    /**
     * Delete sample by ID
     *
     * @param id sample ID
     */
    void delete(ULong id);
}