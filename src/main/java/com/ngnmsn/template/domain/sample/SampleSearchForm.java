package com.ngnmsn.template.domain.sample;

import lombok.Data;

@Data
public class SampleSearchForm {

  private String displayId = "";

  private String text1 = "";

  private static final String QUERY_PARAMETER = "?displayId=%s&text1=%s";

  public String generateQueryParameter() {
    return String.format(QUERY_PARAMETER, this.displayId, this.text1);
  }
}
