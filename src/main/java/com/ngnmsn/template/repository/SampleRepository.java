package com.ngnmsn.template.repository;

import java.io.Serializable;
import java.util.List;

import com.ngnmsn.template.domain.sample.SampleUpdateForm;
import org.jooq.types.ULong;

import com.ngnmsn.template.domain.sample.SampleResult;

public interface SampleRepository extends Serializable {
    public List<SampleResult> search(String displayId, String text1);
    public SampleResult findByDisplayId(String displayId);
    public void insert(String displayId, String text1, int num1);
    public void update(ULong id, String text1, int num1);
    public void delete(ULong id);
}
