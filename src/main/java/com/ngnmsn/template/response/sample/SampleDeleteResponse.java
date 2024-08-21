package com.ngnmsn.template.response.sample;

import com.ngnmsn.template.form.sample.SampleDeleteForm;
import lombok.Data;

/**
 * SampleDeleteResponse
 */
@Data
public class SampleDeleteResponse {

  String sampleDeleteDisplayId;
  SampleDeleteForm sampleDeleteForm;
}
