package com.ngnmsn.template.service;

import java.util.List;
import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleSearchForm;
import com.ngnmsn.template.repository.SampleRepository;
import com.ngnmsn.template.repository.impl.SampleRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.jooq.types.ULong;

@Service
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SampleRepositoryImpl sampleRepositoryImpl;

    public List<SampleResult> search(SampleSearchForm form) {

        String text1 = form.getText1();
        return sampleRepositoryImpl.search(text1);
    }

    public SampleResult detail(String id) {
        ULong idStr = ULong.valueOf(id);
        return sampleRepository.findById(idStr);
    }
}
