package com.ngnmsn.template.presentation.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SampleCreateRequest {
    
    @NotBlank(message = "テキストは必須です")
    @Size(max = 100, message = "テキストは100文字以内で入力してください")
    private String text1;
    
    @NotNull(message = "数値は必須です")
    @Min(value = 0, message = "数値は0以上で入力してください")
    @Max(value = 9999, message = "数値は9999以下で入力してください")
    private Integer num1;
    
    public SampleCreateRequest() {}
    
    public SampleCreateRequest(String text1, Integer num1) {
        this.text1 = text1;
        this.num1 = num1;
    }
    
    public String getText1() { return text1; }
    public Integer getNum1() { return num1; }
    
    public void setText1(String text1) { this.text1 = text1; }
    public void setNum1(Integer num1) { this.num1 = num1; }
}