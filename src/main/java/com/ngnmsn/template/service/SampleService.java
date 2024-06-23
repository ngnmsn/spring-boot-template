package com.ngnmsn.template.service;

import java.util.List;

import com.ngnmsn.template.domain.sample.SampleCreateForm;
import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleSearchForm;
import com.ngnmsn.template.domain.sample.SampleUpdateForm;
import com.ngnmsn.template.repository.impl.SampleRepositoryImpl;
import com.ngnmsn.template.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.jooq.types.ULong;

@Service
public class SampleService {

    @Autowired
    private SampleRepositoryImpl sampleRepositoryImpl;

    @Autowired
    private StringUtil stringUtil;

    public List<SampleResult> search(SampleSearchForm form) {

        String displayId = form.getDisplayId();
        String text1 = form.getText1();
        return sampleRepositoryImpl.search(displayId, text1);
    }

    public SampleResult detail(String displayId) {
        return sampleRepositoryImpl.findByDisplayId(displayId);
    }

    public void create(SampleCreateForm form) {
        String displayId = stringUtil.generateUUID();
        sampleRepositoryImpl.insert(displayId, form.getText1(), form.getNum1());
    }

    public void update(ULong id, SampleUpdateForm form) {
        sampleRepositoryImpl.update(id, form.getText1(), form.getNum1());
    }

    public void delete(ULong id) {
        sampleRepositoryImpl.delete(id);
    }
}
