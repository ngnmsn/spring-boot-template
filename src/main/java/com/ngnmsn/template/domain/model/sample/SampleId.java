package com.ngnmsn.template.domain.model.sample;

import org.jooq.types.ULong;

/**
 * SampleId value object
 */
public class SampleId {

  private final ULong value;

  public SampleId(ULong value) {
    if (value == null) {
      throw new IllegalArgumentException("SampleId cannot be null");
    }
    this.value = value;
  }

  public ULong getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SampleId sampleId = (SampleId) o;
    return value.equals(sampleId.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return "SampleId{" + "value=" + value + '}';
  }
}