package com.ngnmsn.template.domain.model;

import com.ngnmsn.template.domain.model.sample.DisplayId;
import com.ngnmsn.template.domain.model.sample.Sample;

public class SampleTestDataBuilder {
    private DisplayId displayId = new DisplayId("123ABCDEFGHIJKLMNOPQRSTUVWXYZ1234");
    private SampleText text1 = new SampleText("テストテキスト");
    private SampleNumber num1 = new SampleNumber(100);
    
    public static SampleTestDataBuilder aSample() {
        return new SampleTestDataBuilder();
    }
    
    public SampleTestDataBuilder withDisplayId(String displayId) {
        this.displayId = new DisplayId(displayId);
        return this;
    }
    
    public SampleTestDataBuilder withText(String text) {
        this.text1 = new SampleText(text);
        return this;
    }
    
    public SampleTestDataBuilder withNumber(Integer number) {
        this.num1 = new SampleNumber(number);
        return this;
    }
    
    public SampleTestDataBuilder withLongText() {
        this.text1 = new SampleText("a".repeat(80));
        return this;
    }
    
    public Sample build() {
        return new Sample(displayId, text1, num1);
    }
}