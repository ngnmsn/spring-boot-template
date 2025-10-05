package com.ngnmsn.template.presentation.web;

import com.ngnmsn.template.consts.WebConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ExceptionController
 */
@Controller
@ControllerAdvice
public class ExceptionController {

  private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

  @ExceptionHandler(Exception.class)
  public String exceptionHandler(Exception e, Model model) {
    log.error(e.getMessage());
    return WebConst.TEMPLATE_ERROR;
  }
}