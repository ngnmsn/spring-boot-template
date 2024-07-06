package com.ngnmsn.template.domain.auth;

import lombok.Data;

@Data
public class AuthResult {

  String displayId;
  String loginId;
  String password;
  String userName;
}
