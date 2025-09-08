package com.ngnmsn.template.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ngnmsn.template.domain.model.sample.SampleResult;
import com.ngnmsn.template.domain.model.sample.SampleResults;
import com.ngnmsn.template.domain.service.SampleService;
import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.command.SampleUpdateCommand;
import com.ngnmsn.template.application.query.SampleSearchQuery;
import com.ngnmsn.template.application.port.SampleRepositoryPort;
import com.ngnmsn.template.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.jooq.types.ULong;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * SampleServiceTestクラス
 */
@SpringBootTest
public class SampleServiceTest {

  @MockitoBean
  private SampleRepositoryPort sampleRepository;

  @MockitoBean
  private StringUtil stringUtil;

  @Autowired
  private SampleService sampleService;

  @DisplayName("search()の正常系テスト")
  @Test
  public void testSearchSuccess() {
    SampleResults expects = setExpects();
    when(sampleRepository.search(any(), any(), anyInt(), anyInt())).thenReturn(expects);

    SampleSearchQuery query = new SampleSearchQuery("", "", 1, 20);

    SampleResults results = sampleService.search(query);

    assertThat(results.getSampleResultList().getFirst().getId(),
        is(expects.getSampleResultList().getFirst().getId()));
    assertThat(results.getSampleResultList().getFirst().getDisplayId(),
        is(expects.getSampleResultList().getFirst().getDisplayId()));
    assertThat(results.getSampleResultList().getFirst().getText1(),
        is(expects.getSampleResultList().getFirst().getText1()));
    assertThat(results.getSampleResultList().getFirst().getNum1(),
        is(expects.getSampleResultList().getFirst().getNum1()));
  }

  @DisplayName("detail()の正常系テスト")
  @Test
  public void testDetailSuccess() {
    SampleResult expect = setExpect();
    when(sampleRepository.findByDisplayId(any())).thenReturn(expect);

    String displayId = "001ABCDEFGHIJKLMNOPQRSTUVWXYZABC";

    SampleResult result = sampleService.detail(displayId);

    assertThat(result.getId(), is(expect.getId()));
    assertThat(result.getDisplayId(), is(expect.getDisplayId()));
    assertThat(result.getText1(), is(expect.getText1()));
    assertThat(result.getNum1(), is(expect.getNum1()));
  }

  @DisplayName("create()の正常系テスト")
  @Test
  public void testCreateSuccess() {
    when(stringUtil.generateUuid()).thenReturn("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
    SampleCreateCommand command = new SampleCreateCommand("test1", 1);

    doNothing().when(sampleRepository)
        .insert("001ABCDEFGHIJKLMNOPQRSTUVWXYZAB", command.getText1(), command.getNum1());

    sampleService.create(command);
    verify(sampleRepository, times(1)).insert("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC",
        command.getText1(), command.getNum1());
  }

  @DisplayName("update()の正常系テスト")
  @Test
  public void testUpdateSuccess() {
    SampleUpdateCommand command = new SampleUpdateCommand("test1", 1);

    doNothing().when(sampleRepository)
        .update(ULong.valueOf(1), command.getText1(), command.getNum1());

    sampleService.update(ULong.valueOf(1), command);
    verify(sampleRepository, times(1)).update(ULong.valueOf(1), command.getText1(),
        command.getNum1());
  }

  @DisplayName("delete()の正常系テスト")
  @Test
  public void testDeleteSuccess() {
    doNothing().when(sampleRepository).delete(ULong.valueOf(1));

    sampleService.delete(ULong.valueOf(1));
    verify(sampleRepository, times(1)).delete(ULong.valueOf(1));
  }

  private SampleResult setExpect() {
    SampleResult expect = new SampleResult();
    expect.setId(ULong.valueOf(1));
    expect.setDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
    expect.setText1("test1");
    expect.setNum1(1);
    return expect;
  }

  private SampleResults setExpects() {
    SampleResults expects = new SampleResults();
    expects.setResultCount(1);
    List<SampleResult> expectList = new ArrayList<>();
    SampleResult expect = setExpect();
    expectList.add(expect);
    expects.setSampleResultList(expectList);
    return expects;
  }
}