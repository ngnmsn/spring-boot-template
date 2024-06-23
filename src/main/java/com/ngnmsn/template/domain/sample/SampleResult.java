package com.ngnmsn.template.domain.sample;

import lombok.Data;
import org.jooq.types.ULong;

@Data
public class SampleResult {
    private ULong id;
    private String displayId;
    private String text1;
    private int num1;
}
