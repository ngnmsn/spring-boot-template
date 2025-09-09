package com.ngnmsn.template.presentation.response.sample;

import com.ngnmsn.template.presentation.form.sample.SampleUpdateForm;
import lombok.Data;

@Data
public class SampleUpdateResponse {

  private String sampleUpdateDisplayId;
  private SampleUpdateForm sampleUpdateForm;
}
