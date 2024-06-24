package com.ngnmsn.template.controller;

import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleSearchForm;
import com.ngnmsn.template.repository.impl.SampleRepositoryImpl;
import com.ngnmsn.template.service.SampleService;
import org.jooq.types.ULong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private SampleController sampleController;

    @MockBean
    private SampleService sampleService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sampleController).build();
    }

    @DisplayName("list()の正常系テスト")
    @Test
    public void testListSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/sample"))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/list"));
    }

    @DisplayName("search()の正常系テスト")
    @Test
    public void testSearchSuccess() throws Exception{

        List<SampleResult> expectList = new ArrayList<>() {{
            add(new SampleResult() {{
                setId(ULong.valueOf(1));
                setDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
                setText1("test1");
                setNum1(1);
            }});
        }};

        final MultiValueMap<String, String> formMap = new LinkedMultiValueMap<>() {{
            add("displayId", "001");
            add("text1", "test");
        }};

        when(sampleService.search(any())).thenReturn(expectList);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/sample/search").params(formMap))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/list"));
    }
}
