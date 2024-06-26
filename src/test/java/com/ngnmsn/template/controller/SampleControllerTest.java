package com.ngnmsn.template.controller;

import com.ngnmsn.template.domain.sample.SampleCreateForm;
import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.service.SampleService;
import jakarta.servlet.http.HttpSession;
import org.jooq.types.ULong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
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

    @Mock
    private HttpSession session;

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

    @DisplayName("detail()の正常系テスト")
    @Test
    public void testDetailSuccess() throws Exception{

        SampleResult expect = new SampleResult() {{
            setId(ULong.valueOf(1));
            setDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
            setText1("test1");
            setNum1(1);
        }};

        when(sampleService.detail(any())).thenReturn(expect);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC"))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/detail"));
    }

    @DisplayName("create()の正常系テスト")
    @Test
    public void testCreateSuccess() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/sample/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/create"));
    }

    @DisplayName("createConfirm()の正常系テスト")
    @Test
    public void testCreateConfirmSuccess() throws Exception{

        final MultiValueMap<String, String> formMap = new LinkedMultiValueMap<>() {{
            add("text1", "test1");
            add("num1", "1");
        }};

        doNothing().when(session).setAttribute(any(),any());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sample/create/confirm")
                        .params(formMap))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/create_confirm"));
    }

    @DisplayName("returnCreate()の正常系テスト")
    @Test
    public void testReturnCreateSuccess() throws Exception{

        SampleCreateForm form = new SampleCreateForm() {{
            setText1("test1");
            setNum1(1);
        }};

        when(session.getAttribute(any())).thenReturn(form);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/sample/create/confirm"))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/create"));
    }

    @DisplayName("createProcess()の正常系テスト")
    @Test
    public void testCreateProcessSuccess() throws Exception{

        SampleCreateForm form = new SampleCreateForm() {{
            setText1("test1");
            setNum1(1);
        }};

        when(session.getAttribute(any())).thenReturn(form);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sample/create/process"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sample/create/complete"));
    }

    @DisplayName("createComplete()の正常系テスト")
    @Test
    public void testCreateCompleteSuccess() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/sample/create/complete"))
                .andExpect(status().isOk())
                .andExpect(view().name("sample/create_complete"));
    }
}
