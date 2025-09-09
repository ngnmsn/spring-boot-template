package com.ngnmsn.template.presentation.web;

import com.ngnmsn.template.application.service.SampleApplicationService;
import com.ngnmsn.template.application.response.SampleDetailResponse;
import com.ngnmsn.template.domain.model.SampleSearchResults;
import com.ngnmsn.template.application.exception.SampleNotFoundException;
import com.ngnmsn.template.application.exception.SampleValidationException;
import com.ngnmsn.template.domain.exception.SampleBusinessException;
import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.query.SampleSearchQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SampleController.class)
class SampleControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private SampleApplicationService sampleApplicationService;
    
    @Test
    void shouldDisplaySearchPage() throws Exception {
        // Given
        var searchResults = new SampleSearchResults(Collections.emptyList(), 0, 1, 20);
        when(sampleApplicationService.search(any(SampleSearchQuery.class)))
            .thenReturn(searchResults);
        
        // When & Then
        mockMvc.perform(get("/samples"))
            .andExpect(status().isOk())
            .andExpect(view().name("sample/search"))
            .andExpect(model().attributeExists("results"));
    }
    
    @Test
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
    void shouldHandleInvalidIdForDetail() throws Exception {
        // When & Then
        mockMvc.perform(get("/samples/0"))
            .andExpect(status().isOk())
            .andExpect(view().name("error"))
            .andExpect(model().attribute("errorMessage", "不正なIDです"));
    }
    
    @Test
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
    void shouldDisplayCreateForm() throws Exception {
        // When & Then
        mockMvc.perform(get("/samples/create"))
            .andExpect(status().isOk())
            .andExpect(view().name("sample/create"))
            .andExpect(model().attributeExists("createForm"));
    }
    
    @Test
    void shouldCreateSampleSuccessfully() throws Exception {
        // Given
        var response = new SampleDetailResponse(1L, "123ABC...", "テスト", 100, 
                                             LocalDateTime.now(), LocalDateTime.now(),
                                             Collections.emptyList());
        when(sampleApplicationService.createSample(any(SampleCreateCommand.class)))
            .thenReturn(response);
        
        // When & Then
        mockMvc.perform(post("/samples")
                .param("text1", "テスト")
                .param("num1", "100"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/samples/1"));
    }
    
    @Test
    void shouldHandleValidationErrors() throws Exception {
        // When & Then
        mockMvc.perform(post("/samples")
                .param("text1", "") // 空文字でバリデーションエラー
                .param("num1", "100"))
            .andExpect(status().isOk())
            .andExpect(view().name("sample/create"));
    }
    
    @Test
    void shouldHandleBusinessException() throws Exception {
        // Given
        when(sampleApplicationService.createSample(any(SampleCreateCommand.class)))
            .thenThrow(new SampleBusinessException("重複エラー"));
        
        // When & Then
        mockMvc.perform(post("/samples")
                .param("text1", "テスト")
                .param("num1", "100"))
            .andExpect(status().isOk())
            .andExpect(view().name("sample/create"))
            .andExpect(model().attribute("errorMessage", "重複エラー"));
    }
    
    @Test
    void shouldHandleValidationException() throws Exception {
        // Given
        when(sampleApplicationService.createSample(any(SampleCreateCommand.class)))
            .thenThrow(new SampleValidationException("バリデーションエラー"));
        
        // When & Then
        mockMvc.perform(post("/samples")
                .param("text1", "テスト")
                .param("num1", "100"))
            .andExpect(status().isOk())
            .andExpect(view().name("sample/create"))
            .andExpect(model().attribute("errorMessage", "バリデーションエラー"));
    }
    
    @Test
    void shouldDisplayEditForm() throws Exception {
        // Given
        var response = new SampleDetailResponse(1L, "123ABC...", "テスト", 100, 
                                             LocalDateTime.now(), LocalDateTime.now(),
                                             Collections.emptyList());
        when(sampleApplicationService.findById(1L)).thenReturn(response);
        
        // When & Then
        mockMvc.perform(get("/samples/1/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("sample/edit"))
            .andExpect(model().attributeExists("sample"))
            .andExpect(model().attributeExists("updateForm"));
    }
    
    @Test
    void shouldHandleInvalidIdForEdit() throws Exception {
        // When & Then
        mockMvc.perform(get("/samples/0/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("error"))
            .andExpect(model().attribute("errorMessage", "不正なIDです"));
    }
    
    @Test
    void shouldDeleteSampleSuccessfully() throws Exception {
        // When & Then
        mockMvc.perform(delete("/samples/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/samples"));
    }
    
    @Test
    void shouldHandleInvalidIdForDelete() throws Exception {
        // When & Then
        mockMvc.perform(delete("/samples/0"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/samples"));
    }
    
    @Test
    void shouldHandleBulkDeleteSuccessfully() throws Exception {
        // When & Then
        mockMvc.perform(post("/samples/bulk-delete")
                .param("selectedIds", "1", "2", "3"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/samples"));
    }
    
    @Test
    void shouldHandleEmptyBulkDelete() throws Exception {
        // When & Then
        mockMvc.perform(post("/samples/bulk-delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/samples"));
    }
}