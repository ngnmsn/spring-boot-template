package com.ngnmsn.template.repository;

import static com.ngnmsn.template.Tables.SAMPLES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.ngnmsn.template.domain.model.sample.SampleResult;
import com.ngnmsn.template.domain.model.sample.SampleResults;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.types.ULong;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SampleRepositoryTestクラス
 */
@SpringBootTest
public class SampleRepositoryTest {

  @Autowired
  private SampleRepository sampleRepository;

  @Autowired
  private DSLContext jooq;

  @DisplayName("search()の正常系テスト")
  @Test
  public void testSearchSuccess() {
    SampleResults expects = new SampleResults();
    expects.setResultCount(1);
    List<SampleResult> expectList = new ArrayList<>() {
      {
        add(new SampleResult() {
          {
            setId(ULong.valueOf(1));
            setDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
            setText1("test1");
            setNum1(1);
          }
        });
      }
    };
    expects.setSampleResultList(expectList);

    SampleResults results = sampleRepository.search("001", "test", 1, 30);

    assertThat(results.getResultCount(), is(expects.getResultCount()));

    int i = 0;
    for (SampleResult result : results.getSampleResultList()) {
      assertThat(result.getId(), is(expects.getSampleResultList().get(i).getId()));
      assertThat(result.getDisplayId(), is(expects.getSampleResultList().get(i).getDisplayId()));
      assertThat(result.getText1(), is(expects.getSampleResultList().get(i).getText1()));
      assertThat(result.getNum1(), is(expects.getSampleResultList().get(i).getNum1()));
      i++;
    }
  }

  @DisplayName("findByDisplayId()の正常系テスト")
  @Test
  public void testFindByDisplayIdSuccess() {
    SampleResult expect = new SampleResult() {
      {
        setId(ULong.valueOf(1));
        setDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        setText1("test1");
        setNum1(1);
      }
    };
    SampleResult result = sampleRepository.findByDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");

    assertThat(result.getId(), is(expect.getId()));
    assertThat(result.getDisplayId(), is(expect.getDisplayId()));
    assertThat(result.getText1(), is(expect.getText1()));
    assertThat(result.getNum1(), is(expect.getNum1()));
  }

  @DisplayName("insert()の正常系テスト")
  @Test
  public void testInsertSuccess() {
    SampleResult expect = new SampleResult() {
      {
        setId(ULong.valueOf(41));
        setDisplayId("041ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        setText1("test41");
        setNum1(41);
      }
    };
    sampleRepository.insert("041ABCDEFGHIJKLMNOPQRSTUVWXYZABC", "test41", 41);

    Record record = jooq.select()
        .from(SAMPLES)
        .where(SAMPLES.ID.eq(ULong.valueOf(41)))
        .fetchSingle();

    SampleResult result = new SampleResult() {
      {
        setId(record.getValue(SAMPLES.ID));
        setDisplayId(record.getValue(SAMPLES.DISPLAY_ID));
        setText1(record.getValue(SAMPLES.TEXT1));
        setNum1(record.getValue(SAMPLES.NUM1));
      }
    };

    assertThat(result.getId(), is(expect.getId()));
    assertThat(result.getDisplayId(), is(expect.getDisplayId()));
    assertThat(result.getText1(), is(expect.getText1()));
    assertThat(result.getNum1(), is(expect.getNum1()));

    jooq.delete(SAMPLES)
        .where(SAMPLES.ID.eq(ULong.valueOf(41)))
        .execute();
  }

  @DisplayName("update()の正常系テスト")
  @Test
  public void testUpdateSuccess() {
    SampleResult expect = new SampleResult() {
      {
        setId(ULong.valueOf(5));
        setDisplayId("005ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        setText1("test55");
        setNum1(55);
      }
    };
    sampleRepository.update(ULong.valueOf(5), "test55", 55);

    Record record = jooq.select()
        .from(SAMPLES)
        .where(SAMPLES.ID.eq(ULong.valueOf(5)))
        .fetchSingle();

    SampleResult result = new SampleResult() {
      {
        setId(record.getValue(SAMPLES.ID));
        setDisplayId(record.getValue(SAMPLES.DISPLAY_ID));
        setText1(record.getValue(SAMPLES.TEXT1));
        setNum1(record.getValue(SAMPLES.NUM1));
      }
    };

    assertThat(result.getId(), is(expect.getId()));
    assertThat(result.getDisplayId(), is(expect.getDisplayId()));
    assertThat(result.getText1(), is(expect.getText1()));
    assertThat(result.getNum1(), is(expect.getNum1()));

    jooq.update(SAMPLES)
        .set(SAMPLES.TEXT1, "test5")
        .set(SAMPLES.NUM1, 5)
        .where(SAMPLES.ID.eq(ULong.valueOf(5)))
        .execute();
  }

  @DisplayName("delete()の正常系テスト")
  @Test
  public void testDeleteSuccess() {

    sampleRepository.delete(ULong.valueOf(5));

    Result<Record> records = jooq.select()
        .from(SAMPLES)
        .where(SAMPLES.ID.eq(ULong.valueOf(5)))
        .fetch();

    assertThat(records.size(), is(0));

    jooq.insertInto(SAMPLES, SAMPLES.ID, SAMPLES.DISPLAY_ID, SAMPLES.TEXT1, SAMPLES.NUM1)
        .values(ULong.valueOf(5), "005ABCDEFGHIJKLMNOPQRSTUVWXYZABC", "test5", 5)
        .execute();
  }

}
