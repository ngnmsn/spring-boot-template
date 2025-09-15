package com.ngnmsn.template.presentation.response;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApiResponseTest {
    
    @Test
    void shouldCreateSuccessResponse() {
        String data = "test data";
        ApiResponse<String> response = ApiResponse.success(data);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getData()).isEqualTo(data);
        assertThat(response.getMessage()).isEqualTo("成功");
        assertThat(response.getErrors()).isEmpty();
        assertThat(response.getTimestamp()).isNotNull();
    }
    
    @Test
    void shouldCreateSuccessResponseWithMessage() {
        var data = "test data";
        var message = "カスタムメッセージ";
        var response = ApiResponse.success(message, data);
        
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getData()).isEqualTo(data);
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getErrors()).isEmpty();
        assertThat(response.getTimestamp()).isNotNull();
    }
    
    @Test
    void shouldCreateSuccessResponseWithoutData() {
        var message = "成功メッセージ";
        var response = ApiResponse.successMessage(message);
        
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getData()).isNull();
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getErrors()).isEmpty();
        assertThat(response.getTimestamp()).isNotNull();
    }
    
    @Test
    void shouldCreateErrorResponse() {
        var message = "エラーメッセージ";
        var response = ApiResponse.error(message);
        
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getData()).isNull();
        assertThat(response.getErrors()).isEmpty();
        assertThat(response.getTimestamp()).isNotNull();
    }
    
    @Test
    void shouldCreateErrorResponseWithErrors() {
        var message = "エラーメッセージ";
        var errors = List.of("エラー1", "エラー2");
        var response = ApiResponse.error(message, errors);
        
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getData()).isNull();
        assertThat(response.getErrors()).containsExactly("エラー1", "エラー2");
        assertThat(response.getTimestamp()).isNotNull();
    }
    
    @Test
    void shouldCreateValidationErrorResponse() {
        var errors = List.of("フィールド1が不正です", "フィールド2が不正です");
        var response = ApiResponse.validationError(errors);
        
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getMessage()).isEqualTo("入力値に問題があります");
        assertThat(response.getData()).isNull();
        assertThat(response.getErrors()).containsExactly("フィールド1が不正です", "フィールド2が不正です");
        assertThat(response.getTimestamp()).isNotNull();
    }
    
    @Test
    void shouldReturnImmutableErrorsList() {
        var errors = new ArrayList<>(List.of("エラー1", "エラー2"));
        var response = ApiResponse.error("メッセージ", errors);

        var returnedErrors = response.getErrors();
        assertThat(returnedErrors).containsExactly("エラー1", "エラー2");

        errors.clear();
        assertThat(response.getErrors()).containsExactly("エラー1", "エラー2");

        assertThat(returnedErrors).isInstanceOf(List.class);
        assertThatThrownBy(() -> returnedErrors.add("新しいエラー"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}