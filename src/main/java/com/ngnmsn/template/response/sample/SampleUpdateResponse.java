package com.ngnmsn.template.response.sample;

import com.ngnmsn.template.form.sample.SampleUpdateForm;
import lombok.Data;

@Data
public class SampleUpdateResponse {

  private String sampleUpdateDisplayId;
  private SampleUpdateForm sampleUpdateForm;
}
