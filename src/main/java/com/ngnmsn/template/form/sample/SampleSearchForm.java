package com.ngnmsn.template.form.sample;

import lombok.Data;

/**
 * SampleSearchFormクラス
 */
@Data
public class SampleSearchForm {

  private static final String QUERY_PARAMETER = "?displayId=%s&text1=%s&page=%d&maxNumPerPage=%d";
  private String displayId = "";
  private String text1 = "";
  private int page = 1;
  private int maxNumPerPage = 30;

  /**
   * generateQueryParameter
   *
   * @return String クエリパラメータ
   */
  public String generateQueryParameter() {
    return String.format(
        QUERY_PARAMETER,
        this.displayId,
        this.text1,
        this.page,
        this.maxNumPerPage
    );
  }
}
