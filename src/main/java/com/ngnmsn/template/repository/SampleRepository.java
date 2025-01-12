package com.ngnmsn.template.repository;

import static com.ngnmsn.template.Tables.SAMPLES;
import static org.jooq.impl.DSL.count;

import com.ngnmsn.template.domain.model.sample.SampleResult;
import com.ngnmsn.template.domain.model.sample.SampleResults;
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
public class SampleRepository {

  @Autowired
  private DSLContext jooq;

  public SampleResults search(String displayId, String text1, int page, int maxNumPerPage) {
    SampleResults sampleResults = new SampleResults();

    Record count = jooq.select(count())
        .from(SAMPLES)
        .where(SAMPLES.DISPLAY_ID.like("%" + displayId + "%"))
        .and(SAMPLES.TEXT1.like("%" + text1 + "%"))
        .fetchSingle();
    int resultCount = count.getValue(count());

    sampleResults.setResultCount(resultCount);

    Result<Record> results = jooq.select()
        .from(SAMPLES)
        .where(SAMPLES.DISPLAY_ID.like("%" + displayId + "%"))
        .and(SAMPLES.TEXT1.like("%" + text1 + "%"))
        .limit(maxNumPerPage)
        .offset((page - 1) * maxNumPerPage)
        .fetch();
    List<SampleResult> sampleResultList = new ArrayList<SampleResult>();
    for (Record r : results) {
      SampleResult sampleResult = new SampleResult();
      sampleResult.setId(r.getValue(SAMPLES.ID));
      sampleResult.setDisplayId(r.getValue(SAMPLES.DISPLAY_ID));
      sampleResult.setText1(r.getValue(SAMPLES.TEXT1));
      sampleResult.setNum1(r.getValue(SAMPLES.NUM1));
      sampleResultList.add(sampleResult);
    }
    sampleResults.setSampleResultList(sampleResultList);
    return sampleResults;
  }

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

  public void insert(String displayId, String text1, Integer num1) {
    jooq.insertInto(SAMPLES, SAMPLES.DISPLAY_ID, SAMPLES.TEXT1, SAMPLES.NUM1)
        .values(displayId, text1, num1)
        .execute();
  }

  public void update(ULong id, String text1, Integer num1) {
    jooq.update(SAMPLES)
        .set(SAMPLES.TEXT1, text1)
        .set(SAMPLES.NUM1, num1)
        .where(SAMPLES.ID.eq(id))
        .execute();
  }

  public void delete(ULong id) {
    jooq.delete(SAMPLES)
        .where(SAMPLES.ID.eq(id))
        .execute();
  }
}
