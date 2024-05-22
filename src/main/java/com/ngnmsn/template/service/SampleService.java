package com.ngnmsn.template.service;

import java.util.List;
import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleSearchForm;
import com.ngnmsn.template.repository.impl.SampleRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.jooq.types.ULong;

@Service
public class SampleService {

    @Autowired
    private SampleRepositoryImpl sampleRepository;

    public List<SampleResult> search(SampleSearchForm form) {

        ULong id = form.getId();
        String text1 = form.getText1();
        int num1 = form.getNum1();
        return sampleRepository.search(id,text1, num1);
    }
}
