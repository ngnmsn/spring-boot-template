package com.ngnmsn.template.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.ngnmsn.template.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;
import org.springframework.stereotype.Repository;
import org.jooq.types.ULong;

import com.ngnmsn.template.domain.sample.SampleResult;
import static com.ngnmsn.template.Tables.SAMPLES;

@Repository
public class SampleRepositoryImpl implements SampleRepository {
    @Autowired
    private DSLContext jooq;

    public  SampleRepositoryImpl() {
        super();
    }

    @Override
    public List<SampleResult> search(ULong id, String text1, int num1) {
        Result<Record> results = jooq.select().from(SAMPLES).fetch();
        List<SampleResult> sampleResults = new ArrayList<SampleResult>();
        for(Record r:results){
            SampleResult sampleResult = new SampleResult();
            sampleResult.setId(r.getValue(SAMPLES.ID));
            sampleResult.setText1(r.getValue(SAMPLES.TEXT1));
            sampleResult.setNum1(r.getValue(SAMPLES.NUM1));
            sampleResults.add(sampleResult);
        }
        return sampleResults;
    }


}
