package com.ngnmsn.template.domain.model.auth;

import java.util.List;
import lombok.Data;
/**
 * AuthResultクラス
 */
@Data
public class AuthResult {

  String displayId;
  Long userGroupId;
  String loginId;
  String password;
  String userName;
  List<String> permissionNames;
}
