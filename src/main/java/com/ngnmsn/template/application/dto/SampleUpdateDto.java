package com.ngnmsn.template.application.dto;

/**
 * サンプル更新用DTOクラス
 * アプリケーション層とプレゼンテーション層間でのデータ転送に使用
 */
public class SampleUpdateDto {
    private final String text1;
    private final Integer num1;
    
    public SampleUpdateDto(String text1, Integer num1) {
        this.text1 = text1;
        this.num1 = num1;
    }
    
    public String getText1() {
        return text1;
    }
    
    public Integer getNum1() {
        return num1;
    }
}