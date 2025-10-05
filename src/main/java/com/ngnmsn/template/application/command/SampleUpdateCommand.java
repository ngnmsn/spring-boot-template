package com.ngnmsn.template.application.command;

/**
 * Command for updating an existing sample
 */
public class SampleUpdateCommand {
    private final String text1;
    private final Integer num1;
    
    public SampleUpdateCommand(String text1, Integer num1) {
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