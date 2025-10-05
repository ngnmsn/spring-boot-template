package com.ngnmsn.template.presentation.response.sample;

import com.ngnmsn.template.presentation.form.sample.SampleDeleteForm;
import lombok.Data;

/**
 * SampleDeleteResponse
 */
@Data
public class SampleDeleteResponse {

  String sampleDeleteDisplayId;
  SampleDeleteForm sampleDeleteForm;
}
