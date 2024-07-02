package com.ngnmsn.template.service;

import com.ngnmsn.template.domain.sample.SampleCreateForm;
import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleSearchForm;
import com.ngnmsn.template.domain.sample.SampleUpdateForm;
import com.ngnmsn.template.repository.impl.SampleRepositoryImpl;
import com.ngnmsn.template.util.StringUtil;
import org.jooq.types.ULong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class SampleServiceTest {

    @MockBean
    private SampleRepositoryImpl sampleRepositoryImpl;

    @MockBean
    private StringUtil stringUtil;

    @InjectMocks
    private SampleService sampleService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("search()の正常系テスト")
    @Test
    public void testSearchSuccess() {
        List<SampleResult> expect = setExpectList();
        when(sampleRepositoryImpl.search(any(),any())).thenReturn(expect);

        SampleSearchForm form = new SampleSearchForm();
        form.setDisplayId("");
        form.setText1("");

        List<SampleResult> results = sampleService.search(form);

        assertThat(results.getFirst().getId(), is(expect.getFirst().getId()));
        assertThat(results.getFirst().getDisplayId(), is(expect.getFirst().getDisplayId()));
        assertThat(results.getFirst().getText1(), is(expect.getFirst().getText1()));
        assertThat(results.getFirst().getNum1(), is(expect.getFirst().getNum1()));
    }

    @DisplayName("detail()の正常系テスト")
    @Test
    public void testDetailSuccess() {
        SampleResult expect = setExpect();
        when(sampleRepositoryImpl.findByDisplayId(any())).thenReturn(expect);

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
        when(stringUtil.generateUUID()).thenReturn("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        SampleCreateForm form = new SampleCreateForm();
        form.setText1("test1");
        form.setNum1(1);

        doNothing().when(sampleRepositoryImpl).insert("001ABCDEFGHIJKLMNOPQRSTUVWXYZAB",form.getText1(),form.getNum1());

        sampleService.create(form);
        verify(sampleRepositoryImpl, times(1)).insert("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC",form.getText1(),form.getNum1());
    }

    @DisplayName("update()の正常系テスト")
    @Test
    public void testUpdateSuccess() {
        SampleUpdateForm form = new SampleUpdateForm();
        form.setText1("test1");
        form.setNum1(1);

        doNothing().when(sampleRepositoryImpl).update(ULong.valueOf(1),form.getText1(),form.getNum1());

        sampleService.update(ULong.valueOf(1) ,form);
        verify(sampleRepositoryImpl, times(1)).update(ULong.valueOf(1),form.getText1(),form.getNum1());
    }

    @DisplayName("delete()の正常系テスト")
    @Test
    public void testDeleteSuccess() {
        doNothing().when(sampleRepositoryImpl).delete(ULong.valueOf(1));

        sampleService.delete(ULong.valueOf(1));
        verify(sampleRepositoryImpl, times(1)).delete(ULong.valueOf(1));
    }

    private SampleResult setExpect() {
        SampleResult expect = new SampleResult();
        expect.setId(ULong.valueOf(1));
        expect.setDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        expect.setText1("test1");
        expect.setNum1(1);
        return expect;
    }

    private List<SampleResult> setExpectList() {
        List<SampleResult> expectList = new ArrayList<>();
        SampleResult expect = setExpect();
        expectList.add(expect);
        return expectList;
    }
}