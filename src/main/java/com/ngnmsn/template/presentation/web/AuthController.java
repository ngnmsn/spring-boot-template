package com.ngnmsn.template.presentation.web;

import com.ngnmsn.template.consts.AuthConst;
import com.ngnmsn.template.presentation.form.auth.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 認証コントローラークラス
 */
@Controller
public class AuthController {

  /**
   * loginメソッド
   *
   * @param model Modelクラス
   * @return String ログイン画面
   */
  @GetMapping(AuthConst.URL_AUTH_LOGIN)
  public String login(Model model) {
    LoginForm loginForm = new LoginForm();
    model.addAttribute("loginForm", loginForm);
    return AuthConst.TEMPLATE_AUTH_LOGIN;
  }
}