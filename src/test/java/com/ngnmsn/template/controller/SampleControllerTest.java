package com.ngnmsn.template.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.ngnmsn.template.domain.model.sample.SampleResult;
import com.ngnmsn.template.domain.model.sample.SampleResults;
import com.ngnmsn.template.domain.service.SampleService;
import com.ngnmsn.template.form.sample.SampleCreateForm;
import com.ngnmsn.template.form.sample.SampleSearchForm;
import com.ngnmsn.template.form.sample.SampleUpdateForm;
import java.util.ArrayList;
import java.util.List;
import org.jooq.types.ULong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * SampleControllerTestクラス
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

  MockHttpSession mockHttpSession;
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SampleService sampleService;

  @BeforeEach
  public void setUp() {
    this.mockHttpSession = new MockHttpSession();
  }

  @DisplayName("list()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testListSuccess001() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/list"));
  }

  @DisplayName("list()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {})
  public void testListSuccess002() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("search()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testSearchSuccess001() throws Exception {

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

    when(sampleService.search(any())).thenReturn(expects);
    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/search?displayId=001&text1=test"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/list"));
  }

  @DisplayName("search()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {})
  public void testSearchSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/search?displayId=001&text1=test"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("returnSearch()の正常系テスト(sessionあり)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testReturnSearchSuccess001() throws Exception {

    SampleSearchForm sampleSearchForm = new SampleSearchForm() {
      {
        setDisplayId("001");
        setText1("test");
      }
    };

    mockHttpSession.setAttribute("sampleSearchForm", sampleSearchForm);

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/search/return")
            .with(csrf())
            .session(mockHttpSession))
        .andExpect(status().is3xxRedirection())
        .andExpect(
            redirectedUrl("/sample/search?displayId=001&text1=test&page=1&maxNumPerPage=30"));
  }

  @DisplayName("returnSearch()の正常系テスト(sessionがnull)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testReturnSearchSuccess002() throws Exception {

    mockHttpSession.setAttribute("sampleSearchForm", null);
    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/search/return")
            .session(mockHttpSession))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/sample/search?displayId=&text1=&page=1&maxNumPerPage=30"));
  }

  @DisplayName("returnSearch()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {})
  public void testReturnSearchSuccess003() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/search/return"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("detail()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testDetailSuccess001() throws Exception {

    SampleResult expect = new SampleResult() {
      {
        setId(ULong.valueOf(1));
        setDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        setText1("test1");
        setNum1(1);
      }
    };

    when(sampleService.detail(any())).thenReturn(expect);
    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/detail"));
  }

  @DisplayName("detail()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {})
  public void testDetailSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("create()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testCreateSuccess001() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/create"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/create"));
  }

  @DisplayName("create()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testCreateSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/create"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("createConfirm()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testCreateConfirmSuccess001() throws Exception {

    SampleCreateForm sampleCreateForm = new SampleCreateForm() {
      {
        setText1("test1");
        setNum1(1);
      }
    };

    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/create/confirm")
            .flashAttr("sampleCreateForm", sampleCreateForm)
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/create_confirm"));
  }

  @DisplayName("createConfirm()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testCreateConfirmSuccess002() throws Exception {

    SampleCreateForm sampleCreateForm = new SampleCreateForm() {
      {
        setText1("test1");
        setNum1(1);
      }
    };

    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/create/confirm")
            .flashAttr("sampleCreateForm", sampleCreateForm)
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("createConfirm()の正常系テスト(バリデーションエラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testCreateConfirmSuccess003() throws Exception {

    SampleCreateForm sampleCreateForm = new SampleCreateForm() {
      {
        setText1("");
        setNum1(null);
      }
    };

    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/create/confirm")
            .flashAttr("sampleCreateForm", sampleCreateForm)
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/create"));
  }

  @DisplayName("returnCreate()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testReturnCreateSuccess001() throws Exception {

    SampleCreateForm sampleCreateForm = new SampleCreateForm() {
      {
        setText1("test1");
        setNum1(1);
      }
    };

    mockHttpSession.setAttribute("sampleCreateForm", sampleCreateForm);
    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/create/confirm")
            .session(mockHttpSession))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/create"));
  }

  @DisplayName("returnCreate()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testReturnCreateSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/create/confirm"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("createProcess()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testCreateProcessSuccess001() throws Exception {

    SampleCreateForm sampleCreateForm = new SampleCreateForm() {
      {
        setText1("test1");
        setNum1(1);
      }
    };

    mockHttpSession.setAttribute("sampleCreateForm", sampleCreateForm);
    doNothing().when(sampleService).create(any());
    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/create/process")
            .with(csrf())
            .session(mockHttpSession))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/sample/create/complete"));
  }

  @DisplayName("createProcess()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testCreateProcessSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/create/process")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("createComplete()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testCreateCompleteSuccess001() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/create/complete"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/create_complete"));
  }

  @DisplayName("createComplete()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testCreateCompleteSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/create/complete"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("update()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testUpdateSuccess001() throws Exception {

    SampleResult result = new SampleResult() {
      {
        setId(ULong.valueOf(1));
        setDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        setText1("test1");
        setNum1(1);
      }
    };
    when(sampleService.detail(any())).thenReturn(result);
    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/update"));
  }

  @DisplayName("update()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testUpdateSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("updateConfirm()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testUpdateConfirmSuccess001() throws Exception {
    SampleUpdateForm beforeSampleUpdateForm = new SampleUpdateForm() {
      {
        setText1("test1");
        setNum1(1);
      }
    };
    SampleUpdateForm afterSampleUpdateForm = new SampleUpdateForm() {
      {
        setText1("test11");
        setNum1(11);
      }
    };
    mockHttpSession.setAttribute("sampleUpdateDisplayId", "001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
    mockHttpSession.setAttribute("sampleUpdateForm", beforeSampleUpdateForm);
    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/confirm")
            .flashAttr("sampleUpdateForm", afterSampleUpdateForm)
            .with(csrf())
            .session(mockHttpSession))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/update_confirm"));
  }

  @DisplayName("updateConfirm()の正常系テスト(バリデーションエラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testUpdateConfirmSuccess002() throws Exception {
    SampleUpdateForm beforeSampleUpdateForm = new SampleUpdateForm() {
      {
        setText1("test1");
        setNum1(1);
      }
    };
    SampleUpdateForm afterSampleUpdateForm = new SampleUpdateForm() {
      {
        setText1("");
        setNum1(null);
      }
    };
    mockHttpSession.setAttribute("sampleUpdateDisplayId", "001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
    mockHttpSession.setAttribute("sampleUpdateForm", beforeSampleUpdateForm);
    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/confirm")
            .flashAttr("sampleUpdateForm", afterSampleUpdateForm)
            .with(csrf())
            .session(mockHttpSession))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/update"));
  }

  @DisplayName("updateConfirm()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testUpdateConfirmSuccess003() throws Exception {
    SampleUpdateForm afterSampleUpdateForm = new SampleUpdateForm() {
      {
        setText1("test11");
        setNum1(11);
      }
    };
    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/confirm")
            .flashAttr("sampleUpdateForm", afterSampleUpdateForm)
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("updateConfirm()の正常系テスト(変更なし)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testUpdateConfirmSuccess004() throws Exception {
    SampleUpdateForm sampleUpdateForm = new SampleUpdateForm() {
      {
        setText1("test1");
        setNum1(1);
      }
    };
    mockHttpSession.setAttribute("sampleUpdateDisplayId", "001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
    mockHttpSession.setAttribute("sampleUpdateForm", sampleUpdateForm);
    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/confirm")
            .flashAttr("sampleUpdateForm", sampleUpdateForm)
            .with(csrf())
            .session(mockHttpSession))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/update"))
        .andExpect(model().attribute("alertMessage", "変更がありません。"));
  }

  @DisplayName("returnUpdate()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testReturnUpdateSuccess001() throws Exception {

    SampleUpdateForm sampleUpdateForm = new SampleUpdateForm() {
      {
        setText1("test1");
        setNum1(1);
      }
    };
    mockHttpSession.setAttribute("sampleUpdateDisplayId", "001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
    mockHttpSession.setAttribute("sampleUpdateForm", sampleUpdateForm);
    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/confirm")
            .session(mockHttpSession))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/update"));
  }

  @DisplayName("returnUpdate()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testReturnUpdateSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/confirm"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("updateProcess()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testUpdateProcessSuccess001() throws Exception {

    SampleUpdateForm sampleUpdateForm = new SampleUpdateForm() {
      {
        setText1("test1");
        setNum1(1);
      }
    };

    mockHttpSession.setAttribute("sampleUpdateId", ULong.valueOf(1));
    mockHttpSession.setAttribute("sampleUpdateForm", sampleUpdateForm);
    doNothing().when(sampleService).update(any(), any());
    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/process")
            .with(csrf())
            .session(mockHttpSession))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/complete"));
  }

  @DisplayName("updateProcess()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testUpdateProcessSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/process")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("updateComplete()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testUpdateCompleteSuccess001() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/complete"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/update_complete"));
  }

  @DisplayName("updateComplete()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testUpdateCompleteSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/update/complete"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("deleteConfirm()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testDeleteConfirmSuccess001() throws Exception {

    SampleResult result = new SampleResult() {
      {
        setId(ULong.valueOf(1));
        setDisplayId("001ABCDEFGHIJKLMNOPQRSTUVWXYZABC");
        setText1("test1");
        setNum1(1);
      }
    };

    when(sampleService.detail(any())).thenReturn(result);
    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/delete/confirm"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/delete_confirm"));
  }

  @DisplayName("deleteConfirm()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testDeleteConfirmSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/delete/confirm"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("deleteProcess()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testDeleteProcessSuccess001() throws Exception {

    mockHttpSession.setAttribute("sampleDeleteId", ULong.valueOf(1));
    doNothing().when(sampleService).delete(any());
    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/delete/process")
            .with(csrf())
            .session(mockHttpSession))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/delete/complete"));
  }

  @DisplayName("deleteProcess()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testDeleteProcessSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .post("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/delete/process")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }

  @DisplayName("deleteComplete()の正常系テスト")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-write"})
  public void testDeleteCompleteSuccess001() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/delete/complete"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/delete_complete"));
  }

  @DisplayName("deleteComplete()の正常系テスト(権限エラー)")
  @Test
  @WithMockUser(username = "test", authorities = {"sample-read"})
  public void testDeleteCompleteSuccess002() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/sample/001ABCDEFGHIJKLMNOPQRSTUVWXYZABC/delete/complete"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }
}