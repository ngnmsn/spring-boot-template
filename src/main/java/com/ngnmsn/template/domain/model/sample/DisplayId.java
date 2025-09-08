package com.ngnmsn.template.domain.model.sample;

/**
 * DisplayId value object
 */
public class DisplayId {

  private final String value;

  public DisplayId(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("DisplayId cannot be null or empty");
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DisplayId displayId = (DisplayId) o;
    return value.equals(displayId.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return "DisplayId{" + "value='" + value + '\'' + '}';
  }
}