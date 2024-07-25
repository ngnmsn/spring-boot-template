package com.ngnmsn.template.domain.sample;

import java.util.List;
import lombok.Data;

/**
 * SampleResults
 */
@Data
public class SampleResults {

  int resultCount;
  List<SampleResult> sampleResultList;
}
