package com.ngnmsn.template.domain.sample;

import lombok.Data;
import org.jooq.types.ULong;

@Data
public class SampleSearchForm {

    private ULong id;

    private String text1;

    private int num1;
}
