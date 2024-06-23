package com.ngnmsn.template.service;

import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleSearchForm;
import com.ngnmsn.template.repository.impl.SampleRepositoryImpl;
import org.jooq.types.ULong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class SampleServiceTest {

    @Mock
    private SampleRepositoryImpl sampleRepositoryImpl;

    @InjectMocks
    private SampleService sampleService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("search()の正常系テスト")
    @Test
    public void testSearchSuccess() throws Exception {
        List<SampleResult> expect = new ArrayList<SampleResult>();
        SampleResult sampleResult = new SampleResult();
        sampleResult.setId(ULong.valueOf(1));
        sampleResult.setDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        sampleResult.setText1("test1");
        sampleResult.setNum1(1);
        expect.add(sampleResult);
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

}
