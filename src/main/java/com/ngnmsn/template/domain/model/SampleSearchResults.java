package com.ngnmsn.template.domain.model;

import com.ngnmsn.template.domain.model.sample.Sample;
import java.util.List;

/**
 * Sample search results
 */
public class SampleSearchResults {

  private final List<Sample> results;
  private final int totalCount;
  private final int page;
  private final int maxNumPerPage;

  public SampleSearchResults(List<Sample> results, int totalCount, int page, int maxNumPerPage) {
    if (results == null) {
      throw new IllegalArgumentException("Results cannot be null");
    }
    if (totalCount < 0) {
      throw new IllegalArgumentException("TotalCount cannot be negative");
    }
    if (page < 1) {
      throw new IllegalArgumentException("Page must be greater than 0");
    }
    if (maxNumPerPage < 1) {
      throw new IllegalArgumentException("MaxNumPerPage must be greater than 0");
    }
    this.results = List.copyOf(results);
    this.totalCount = totalCount;
    this.page = page;
    this.maxNumPerPage = maxNumPerPage;
  }

  public List<Sample> getResults() {
    return results;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public int getPage() {
    return page;
  }

  public int getMaxNumPerPage() {
    return maxNumPerPage;
  }

  public boolean hasNextPage() {
    return page * maxNumPerPage < totalCount;
  }

  public boolean hasPreviousPage() {
    return page > 1;
  }

  public int getTotalPages() {
    return (int) Math.ceil((double) totalCount / maxNumPerPage);
  }

  @Override
  public String toString() {
    return "SampleSearchResults{" +
        "results=" + results.size() + " items" +
        ", totalCount=" + totalCount +
        ", page=" + page +
        ", maxNumPerPage=" + maxNumPerPage +
        '}';
  }
}