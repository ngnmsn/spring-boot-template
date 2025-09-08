package com.ngnmsn.template.domain.repository;

import com.ngnmsn.template.domain.model.SampleSearchCriteria;
import com.ngnmsn.template.domain.model.SampleSearchResults;
import com.ngnmsn.template.domain.model.sample.DisplayId;
import com.ngnmsn.template.domain.model.sample.Sample;
import com.ngnmsn.template.domain.model.SampleId;
import java.util.Optional;

/**
 * Sample repository port (interface)
 * Defines the contract for sample data access operations
 */
public interface SampleRepositoryPort {

  /**
   * Find sample by ID
   *
   * @param id sample ID
   * @return sample if found, empty otherwise
   */
  Optional<Sample> findById(SampleId id);

  /**
   * Find sample by display ID
   *
   * @param displayId display ID
   * @return sample if found, empty otherwise
   */
  Optional<Sample> findByDisplayId(DisplayId displayId);

  /**
   * Search samples with criteria
   *
   * @param criteria search criteria
   * @return search results
   */
  SampleSearchResults search(SampleSearchCriteria criteria);

  /**
   * Save sample (create or update)
   *
   * @param sample sample to save
   */
  void save(Sample sample);

  /**
   * Delete sample by ID
   *
   * @param id sample ID to delete
   */
  void deleteById(SampleId id);

  /**
   * Check if sample exists by display ID
   *
   * @param displayId display ID
   * @return true if exists, false otherwise
   */
  boolean existsByDisplayId(DisplayId displayId);
}