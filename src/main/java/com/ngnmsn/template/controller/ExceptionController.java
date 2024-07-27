package com.ngnmsn.template.controller;

import com.ngnmsn.template.consts.WebConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ExceptionController
 */
@Slf4j
@ControllerAdvice
public class ExceptionController {
  @ExceptionHandler(Exception.class)
  public String exceptionHandler(Exception e, Model model) {
    log.error(e.getMessage());
    return WebConst.TEMPLATE_ERROR;
  }
}
