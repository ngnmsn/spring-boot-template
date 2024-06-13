package com.ngnmsn.template.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.ngnmsn.template.repository.SampleRepository;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

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
    public List<SampleResult> search(String text1) {
        Result<Record> results = jooq.select()
                                    .from(SAMPLES)
                                    .where(SAMPLES.TEXT1.like("%" + text1 + "%"))
                                    .fetch();
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

    @Override
    public SampleResult findById(ULong id) {
        Record result = jooq.select()
                            .from(SAMPLES)
                            .where(SAMPLES.ID.eq(id))
                            .fetchSingle();
        SampleResult sampleResult = new SampleResult();
        sampleResult.setId(result.getValue(SAMPLES.ID));
        sampleResult.setText1(result.getValue(SAMPLES.TEXT1));
        sampleResult.setNum1(result.getValue(SAMPLES.NUM1));

        return sampleResult;
    }

    @Override
    public void insert(String text1, int num1) {
        jooq.insertInto(SAMPLES, SAMPLES.TEXT1, SAMPLES.NUM1)
            .values(text1, num1)
            .execute();
    }

    @Override
    public void update(ULong id, String text1, int num1) {
        jooq.update(SAMPLES)
            .set(SAMPLES.TEXT1, text1)
            .set(SAMPLES.NUM1, num1)
            .where(SAMPLES.ID.eq(id))
            .execute();
    }
}
