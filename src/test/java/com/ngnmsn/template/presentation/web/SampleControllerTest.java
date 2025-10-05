package com.ngnmsn.template.presentation.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.dto.SampleUpdateDto;
import com.ngnmsn.template.application.exception.SampleApplicationException;
import com.ngnmsn.template.application.exception.SampleNotFoundException;
import com.ngnmsn.template.application.exception.SampleValidationException;
import com.ngnmsn.template.application.query.SampleSearchQuery;
import com.ngnmsn.template.application.response.SampleDetailResponse;
import com.ngnmsn.template.application.service.SampleApplicationService;
import com.ngnmsn.template.domain.model.SampleSearchResults;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SampleController.class)
class SampleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SampleApplicationService sampleApplicationService;

  @Test
  @WithMockUser
  void shouldDisplaySearchPage() throws Exception {
    // Given
    var searchResults = new SampleSearchResults(Collections.emptyList(), 0, 1, 20);
    when(sampleApplicationService.search(any(SampleSearchQuery.class)))
        .thenReturn(searchResults);

    // When & Then
    mockMvc.perform(get("/samples"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/list"))
        .andExpect(model().attributeExists("results"));
  }

  @Test
  @WithMockUser
  void shouldDisplaySampleDetail() throws Exception {
    // Given
    var response = new SampleDetailResponse(1L, "123ABC...", "テスト", 100,
        LocalDateTime.now(), LocalDateTime.now(),
        Collections.emptyList());
    when(sampleApplicationService.findById(1L)).thenReturn(response);

    // When & Then
    mockMvc.perform(get("/samples/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/detail"))
        .andExpect(model().attributeExists("sample"));
  }

  @Test
  @WithMockUser
  void shouldHandleInvalidIdForDetail() throws Exception {
    // When & Then
    mockMvc.perform(get("/samples/0"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"))
        .andExpect(model().attribute("errorMessage", "不正なIDです"));
  }

  @Test
  @WithMockUser
  void shouldHandleNotFoundExceptionForDetail() throws Exception {
    // Given
    when(sampleApplicationService.findById(999L))
        .thenThrow(new SampleNotFoundException("サンプルが見つかりません"));

    // When & Then
    mockMvc.perform(get("/samples/999"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"))
        .andExpect(model().attribute("errorMessage", "サンプルが見つかりません"));
  }

  @Test
  @WithMockUser
  void shouldDisplayCreateForm() throws Exception {
    // When & Then
    mockMvc.perform(get("/samples/create"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/create"))
        .andExpect(model().attributeExists("createForm"));
  }

  @Test
  @WithMockUser
  void shouldCreateSampleSuccessfully() throws Exception {
    // Given
    var response = new SampleDetailResponse(1L, "123ABC...", "テスト", 100,
        LocalDateTime.now(), LocalDateTime.now(),
        Collections.emptyList());
    when(sampleApplicationService.createSample(any(SampleCreateCommand.class)))
        .thenReturn(response);
    when(sampleApplicationService.extractId(response))
        .thenReturn(1L);

    // When & Then
    mockMvc.perform(post("/samples")
            .with(csrf())
            .param("text1", "テスト")
            .param("num1", "100"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/samples/1"));
  }

  @Test
  @WithMockUser
  void shouldHandleValidationErrors() throws Exception {
    // When & Then
    mockMvc.perform(post("/samples")
            .with(csrf())
            .param("text1", "") // 空文字でバリデーションエラー
            .param("num1", "100"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/create"));
  }

  @Test
  @WithMockUser
  void shouldHandleBusinessException() throws Exception {
    // Given
    when(sampleApplicationService.createSample(any(SampleCreateCommand.class)))
        .thenThrow(new SampleApplicationException("重複エラー"));

    // When & Then
    mockMvc.perform(post("/samples")
            .with(csrf())
            .param("text1", "テスト")
            .param("num1", "100"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/create"))
        .andExpect(model().attribute("errorMessage", "重複エラー"));
  }

  @Test
  @WithMockUser
  void shouldHandleValidationException() throws Exception {
    // Given
    when(sampleApplicationService.createSample(any(SampleCreateCommand.class)))
        .thenThrow(new SampleValidationException("バリデーションエラー"));

    // When & Then
    mockMvc.perform(post("/samples")
            .with(csrf())
            .param("text1", "テスト")
            .param("num1", "100"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/create"))
        .andExpect(model().attribute("errorMessage", "バリデーションエラー"));
  }

  @Test
  @WithMockUser
  void shouldDisplayEditForm() throws Exception {
    // Given
    var response = new SampleDetailResponse(1L, "123ABC...", "テスト", 100,
        LocalDateTime.now(), LocalDateTime.now(),
        Collections.emptyList());
    when(sampleApplicationService.findById(1L)).thenReturn(response);

    var updateDto = new SampleUpdateDto("テスト", 100);
    when(sampleApplicationService.convertToUpdateDto(response)).thenReturn(updateDto);

    // When & Then
    mockMvc.perform(get("/samples/1/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("sample/update"))
        .andExpect(model().attributeExists("sample"))
        .andExpect(model().attributeExists("updateForm"));
  }

  @Test
  @WithMockUser
  void shouldHandleInvalidIdForEdit() throws Exception {
    // When & Then
    mockMvc.perform(get("/samples/0/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"))
        .andExpect(model().attribute("errorMessage", "不正なIDです"));
  }

  @Test
  @WithMockUser
  void shouldDeleteSampleSuccessfully() throws Exception {
    // When & Then
    mockMvc.perform(delete("/samples/1")
            .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/samples"));
  }

  @Test
  @WithMockUser
  void shouldHandleInvalidIdForDelete() throws Exception {
    // When & Then
    mockMvc.perform(delete("/samples/0")
            .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/samples"));
  }

  @Test
  @WithMockUser
  void shouldHandleBulkDeleteSuccessfully() throws Exception {
    // When & Then
    mockMvc.perform(post("/samples/bulk-delete")
            .with(csrf())
            .param("selectedIds", "1", "2", "3"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/samples"));
  }

  @Test
  @WithMockUser
  void shouldHandleEmptyBulkDelete() throws Exception {
    // When & Then
    mockMvc.perform(post("/samples/bulk-delete")
            .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/samples"));
  }
}