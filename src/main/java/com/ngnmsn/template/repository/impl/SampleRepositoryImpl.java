package com.ngnmsn.template.repository.impl;

import static com.ngnmsn.template.Tables.SAMPLES;

import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.repository.SampleRepository;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * SampleRepositoryImplクラス
 */
@Repository
public class SampleRepositoryImpl implements SampleRepository {

  @Autowired
  private DSLContext jooq;

  public SampleRepositoryImpl() {
    super();
  }

  @Override
  public List<SampleResult> search(String displayId, String text1) {
    Result<Record> results = jooq.select()
        .from(SAMPLES)
        .where(SAMPLES.DISPLAY_ID.like("%" + displayId + "%"))
        .and(SAMPLES.TEXT1.like("%" + text1 + "%"))
        .fetch();
    List<SampleResult> sampleResults = new ArrayList<SampleResult>();
    for (Record r : results) {
      SampleResult sampleResult = new SampleResult();
      sampleResult.setId(r.getValue(SAMPLES.ID));
      sampleResult.setDisplayId(r.getValue(SAMPLES.DISPLAY_ID));
      sampleResult.setText1(r.getValue(SAMPLES.TEXT1));
      sampleResult.setNum1(r.getValue(SAMPLES.NUM1));
      sampleResults.add(sampleResult);
    }
    return sampleResults;
  }

  @Override
  public SampleResult findByDisplayId(String displayId) {
    Record result = jooq.select()
        .from(SAMPLES)
        .where(SAMPLES.DISPLAY_ID.eq(displayId))
        .fetchSingle();
    SampleResult sampleResult = new SampleResult();
    sampleResult.setId(result.getValue(SAMPLES.ID));
    sampleResult.setDisplayId(result.getValue(SAMPLES.DISPLAY_ID));
    sampleResult.setText1(result.getValue(SAMPLES.TEXT1));
    sampleResult.setNum1(result.getValue(SAMPLES.NUM1));

    return sampleResult;
  }

  @Override
  public void insert(String displayId, String text1, int num1) {
    jooq.insertInto(SAMPLES, SAMPLES.DISPLAY_ID, SAMPLES.TEXT1, SAMPLES.NUM1)
        .values(displayId, text1, num1)
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

  @Override
  public void delete(ULong id) {
    jooq.delete(SAMPLES)
        .where(SAMPLES.ID.eq(id))
        .execute();
  }
}
