package com.ngnmsn.template.presentation.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final List<String> errors;
    private final LocalDateTime timestamp;
    
    private ApiResponse(boolean success, String message, T data, List<String> errors) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors != null ? new ArrayList<>(errors) : new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }
    
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "成功", data, null);
    }
    
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null);
    }
    
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, message, null, null);
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, null);
    }
    
    public static <T> ApiResponse<T> error(String message, List<String> errors) {
        return new ApiResponse<>(false, message, null, errors);
    }
    
    public static <T> ApiResponse<T> validationError(List<String> errors) {
        return new ApiResponse<>(false, "入力値に問題があります", null, errors);
    }
    
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public List<String> getErrors() { return new ArrayList<>(errors); }
    public LocalDateTime getTimestamp() { return timestamp; }
}