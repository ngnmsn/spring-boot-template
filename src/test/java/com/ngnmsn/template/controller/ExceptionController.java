package com.ngnmsn.template.controller;

import com.ngnmsn.template.consts.WebConst;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ExceptionController
 */
@ControllerAdvice
public class ExceptionController {
  @ExceptionHandler(Exception.class)
  public String exceptionHandler(Exception e, Model model) {
    return WebConst.TEMPLATE_ERROR;
  }
}
