package com.ngnmsn.template.repository;

import java.io.Serializable;
import java.util.List;
import org.jooq.types.ULong;

import com.ngnmsn.template.domain.sample.SampleResult;

public interface SampleRepository extends Serializable {
    public List<SampleResult> search(ULong id, String text1, int num1);
}
