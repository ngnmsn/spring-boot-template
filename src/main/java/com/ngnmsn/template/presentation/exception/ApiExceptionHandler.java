package com.ngnmsn.template.presentation.exception;

import com.ngnmsn.template.application.exception.SampleNotFoundException;
import com.ngnmsn.template.application.exception.SampleValidationException;
import com.ngnmsn.template.domain.exception.SampleBusinessException;
import com.ngnmsn.template.presentation.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
    
    @ExceptionHandler(SampleBusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(SampleBusinessException e) {
        logger.warn("Business exception in API: {}", e.getMessage());
        
        return ResponseEntity.badRequest()
            .body(ApiResponse.error(e.getMessage()));
    }
    
    @ExceptionHandler(SampleValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(SampleValidationException e) {
        logger.warn("Validation exception in API: {}", e.getMessage());
        
        return ResponseEntity.badRequest()
            .body(ApiResponse.error("入力値に問題があります: " + e.getMessage()));
    }
    
    @ExceptionHandler(SampleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(SampleNotFoundException e) {
        logger.warn("Resource not found in API: {}", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error("指定されたリソースが見つかりません"));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        
        return ResponseEntity.badRequest()
            .body(ApiResponse.validationError(errors));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception e) {
        logger.error("Unexpected exception in API", e);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("システムエラーが発生しました"));
    }
}