package com.ngnmsn.template.response.sample;

import com.ngnmsn.template.domain.model.sample.SampleResults;
import com.ngnmsn.template.form.sample.SampleSearchForm;
import lombok.Data;

/**
 * SampleSearchResponse
 */
@Data
public class SampleSearchResponse {

  private SampleSearchForm sampleSearchForm;
  private SampleResults sampleResults;
}
