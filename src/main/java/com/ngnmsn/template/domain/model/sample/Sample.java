package com.ngnmsn.template.domain.model.sample;

import com.ngnmsn.template.domain.exception.SampleBusinessException;
import com.ngnmsn.template.domain.model.SampleText;
import com.ngnmsn.template.domain.model.SampleNumber;
import com.ngnmsn.template.domain.model.CreatedAt;
import com.ngnmsn.template.domain.model.UpdatedAt;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Sample domain entity
 */
public class Sample {

  private final SampleId id;
  private final DisplayId displayId;
  private SampleText text1;
  private SampleNumber num1;
  private final CreatedAt createdAt;
  private UpdatedAt updatedAt;

  public Sample(DisplayId displayId, SampleText text1, SampleNumber num1) {
    this.id = SampleId.generate();
    this.displayId = Objects.requireNonNull(displayId, "表示IDは必須です");
    this.text1 = Objects.requireNonNull(text1, "テキストは必須です");
    this.num1 = Objects.requireNonNull(num1, "数値は必須です");
    this.createdAt = CreatedAt.now();
    this.updatedAt = UpdatedAt.now();
  }

  public Sample(SampleId id, DisplayId displayId, SampleText text1,
                SampleNumber num1, CreatedAt createdAt, UpdatedAt updatedAt) {
    this.id = Objects.requireNonNull(id, "IDは必須です");
    this.displayId = Objects.requireNonNull(displayId, "表示IDは必須です");
    this.text1 = Objects.requireNonNull(text1, "テキストは必須です");
    this.num1 = Objects.requireNonNull(num1, "数値は必須です");
    this.createdAt = Objects.requireNonNull(createdAt, "作成日時は必須です");
    this.updatedAt = Objects.requireNonNull(updatedAt, "更新日時は必須です");
  }

  public void updateText(String newText) {
    if (newText == null || newText.trim().isEmpty()) {
      throw new SampleBusinessException("テキストは必須です");
    }

    var newSampleText = new SampleText(newText);

    if (this.text1.equals(newSampleText)) {
      throw new SampleBusinessException("同じテキストに更新することはできません");
    }

    this.text1 = newSampleText;
    this.updatedAt = UpdatedAt.now();
  }

  public void updateNumber(Integer newNumber) {
    if (newNumber == null) {
      throw new SampleBusinessException("数値は必須です");
    }

    var newSampleNumber = new SampleNumber(newNumber);

    if (newSampleNumber.getValue() < this.num1.getValue()) {
      // ログ出力やイベント発行などの処理
    }

    this.num1 = newSampleNumber;
    this.updatedAt = UpdatedAt.now();
  }

  public boolean canBeDeleted() {
    return this.text1.isNotEmpty() && this.isNotLocked() && this.isNotRecentlyCreated();
  }

  private boolean isNotLocked() {
    return true;
  }

  private boolean isNotRecentlyCreated() {
    var oneDayAgo = LocalDateTime.now().minusDays(1);
    return this.createdAt.getValue().isBefore(oneDayAgo);
  }

  public boolean hasLongText() {
    return this.text1.length() > 50;
  }

  public boolean hasEvenNumber() {
    return this.num1.isEven();
  }

  public void incrementNumber(int increment) {
    this.num1 = this.num1.add(increment);
    this.updatedAt = UpdatedAt.now();
  }

  public SampleId getId() {
    return id;
  }

  public DisplayId getDisplayId() {
    return displayId;
  }

  public SampleText getText1() {
    return text1;
  }

  public SampleNumber getNum1() {
    return num1;
  }

  public CreatedAt getCreatedAt() {
    return createdAt;
  }

  public UpdatedAt getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Sample)) return false;
    Sample sample = (Sample) o;
    return Objects.equals(id, sample.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Sample{" +
        "id=" + id +
        ", displayId=" + displayId +
        ", text1=" + text1 +
        ", num1=" + num1 +
        '}';
  }
}