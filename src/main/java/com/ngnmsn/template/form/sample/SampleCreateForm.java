package com.ngnmsn.template.form.sample;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * SampleCreateForm
 */
@Data
public class SampleCreateForm {

  @NotEmpty(message = "値を入力してください。")
  @Size(min = 1, max = 50, message = "1文字以上50文字以内で入力してください。")
  private String text1;

  @NotNull(message = "値を入力してください。")
  private Integer num1;
}
