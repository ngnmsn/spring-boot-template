package com.ngnmsn.template.controller;

import com.ngnmsn.template.domain.auth.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

  @GetMapping("/login")
  public String login(Model model) {
    LoginForm loginForm = new LoginForm();
    model.addAttribute("loginForm", loginForm);
    return "/auth/login";
  }
}