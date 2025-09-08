package com.ngnmsn.template.domain.model.sample;

/**
 * Sample domain entity
 */
public class Sample {

  private final SampleId id;
  private final DisplayId displayId;
  private final String text1;
  private final Integer num1;

  public Sample(SampleId id, DisplayId displayId, String text1, Integer num1) {
    if (displayId == null) {
      throw new IllegalArgumentException("DisplayId cannot be null");
    }
    this.id = id;
    this.displayId = displayId;
    this.text1 = text1;
    this.num1 = num1;
  }

  public SampleId getId() {
    return id;
  }

  public DisplayId getDisplayId() {
    return displayId;
  }

  public String getText1() {
    return text1;
  }

  public Integer getNum1() {
    return num1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Sample sample = (Sample) o;
    if (id != null && sample.id != null) {
      return id.equals(sample.id);
    }
    return displayId.equals(sample.displayId);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : displayId.hashCode();
  }

  @Override
  public String toString() {
    return "Sample{" +
        "id=" + id +
        ", displayId=" + displayId +
        ", text1='" + text1 + '\'' +
        ", num1=" + num1 +
        '}';
  }
}