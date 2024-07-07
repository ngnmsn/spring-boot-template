package com.ngnmsn.template.repository;

import static com.ngnmsn.template.Tables.SAMPLES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.repository.impl.SampleRepositoryImpl;
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
  private SampleRepositoryImpl sampleRepositoryImpl;

  @Autowired
  private DSLContext jooq;

  @DisplayName("search()の正常系テスト")
  @Test
  public void testSearchSuccess() {
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
    List<SampleResult> resultList = sampleRepositoryImpl.search("001", "test");

    int i = 0;
    for (SampleResult result : resultList) {
      assertThat(result.getId(), is(expectList.get(i).getId()));
      assertThat(result.getDisplayId(), is(expectList.get(i).getDisplayId()));
      assertThat(result.getText1(), is(expectList.get(i).getText1()));
      assertThat(result.getNum1(), is(expectList.get(i).getNum1()));
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
    SampleResult result = sampleRepositoryImpl.findByDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");

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
        setId(ULong.valueOf(6));
        setDisplayId("006ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        setText1("test6");
        setNum1(6);
      }
    };
    sampleRepositoryImpl.insert("006ABCDEFGHIJKLMNOPQRSTUVWXYZABC", "test6", 6);

    Record record = jooq.select()
        .from(SAMPLES)
        .where(SAMPLES.ID.eq(ULong.valueOf(6)))
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
        .where(SAMPLES.ID.eq(ULong.valueOf(6)))
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
    sampleRepositoryImpl.update(ULong.valueOf(5), "test55", 55);

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

    sampleRepositoryImpl.delete(ULong.valueOf(5));

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
