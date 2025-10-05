package com.ngnmsn.template.presentation.exception;

import com.ngnmsn.template.application.exception.SampleNotFoundException;
import com.ngnmsn.template.application.exception.SampleValidationException;
import com.ngnmsn.template.domain.exception.SampleBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * ビジネス例外のハンドリング
     */
    @ExceptionHandler(SampleBusinessException.class)
    public String handleBusinessException(SampleBusinessException e, Model model) {
        logger.warn("Business exception occurred: {}", e.getMessage());
        
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }
    
    /**
     * バリデーション例外のハンドリング
     */
    @ExceptionHandler(SampleValidationException.class)
    public String handleValidationException(SampleValidationException e, Model model) {
        logger.warn("Validation exception occurred: {}", e.getMessage());
        
        model.addAttribute("errorMessage", "入力内容をご確認ください: " + e.getMessage());
        return "error";
    }
    
    /**
     * 404エラー（Not Found）のハンドリング
     */
    @ExceptionHandler(SampleNotFoundException.class)
    public String handleNotFoundException(SampleNotFoundException e, Model model) {
        logger.warn("Resource not found: {}", e.getMessage());
        
        model.addAttribute("errorMessage", "指定されたリソースが見つかりません");
        return "error";
    }
    
    /**
     * 一般的な例外のハンドリング
     */
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        logger.error("Unexpected exception occurred", e);
        
        model.addAttribute("errorMessage", "システムエラーが発生しました。管理者に連絡してください。");
        return "error";
    }
    
    /**
     * バリデーションエラーのハンドリング（REST API用）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException e) {
        
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        
        return ResponseEntity.badRequest().body(errors);
    }
}