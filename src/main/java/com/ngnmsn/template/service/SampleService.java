package com.ngnmsn.template.service;

import java.util.List;

import com.ngnmsn.template.domain.sample.SampleCreateForm;
import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleSearchForm;
import com.ngnmsn.template.domain.sample.SampleUpdateForm;
import com.ngnmsn.template.repository.impl.SampleRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.jooq.types.ULong;

@Service
public class SampleService {

    @Autowired
    private SampleRepositoryImpl sampleRepositoryImpl;

    public List<SampleResult> search(SampleSearchForm form) {

        String text1 = form.getText1();
        return sampleRepositoryImpl.search(text1);
    }

    public SampleResult detail(String id) {
        ULong idStr = ULong.valueOf(id);
        return sampleRepositoryImpl.findById(idStr);
    }

    public void create(SampleCreateForm form) {
        sampleRepositoryImpl.insert(form.getText1(), form.getNum1());
    }

    public void update(ULong id, SampleUpdateForm form) {
        sampleRepositoryImpl.update(id, form.getText1(), form.getNum1());
    }

    public void delete(ULong id) {
        sampleRepositoryImpl.delete(id);
    }
}
