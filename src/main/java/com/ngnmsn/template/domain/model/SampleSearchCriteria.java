package com.ngnmsn.template.domain.model;

import com.ngnmsn.template.domain.model.sample.DisplayId;

/**
 * Sample search criteria
 */
public class SampleSearchCriteria {

  private final DisplayId displayId;
  private final String text1;
  private final Integer page;
  private final Integer maxNumPerPage;

  public SampleSearchCriteria(DisplayId displayId, String text1, Integer page, Integer maxNumPerPage) {
    if (page != null && page < 1) {
      throw new IllegalArgumentException("Page must be greater than 0");
    }
    if (maxNumPerPage != null && maxNumPerPage < 1) {
      throw new IllegalArgumentException("MaxNumPerPage must be greater than 0");
    }
    this.displayId = displayId;
    this.text1 = text1;
    this.page = page != null ? page : 1;
    this.maxNumPerPage = maxNumPerPage != null ? maxNumPerPage : 10;
  }

  public DisplayId getDisplayId() {
    return displayId;
  }

  public String getText1() {
    return text1;
  }

  public Integer getPage() {
    return page;
  }

  public Integer getMaxNumPerPage() {
    return maxNumPerPage;
  }

  @Override
  public String toString() {
    return "SampleSearchCriteria{" +
        "displayId=" + displayId +
        ", text1='" + text1 + '\'' +
        ", page=" + page +
        ", maxNumPerPage=" + maxNumPerPage +
        '}';
  }
}