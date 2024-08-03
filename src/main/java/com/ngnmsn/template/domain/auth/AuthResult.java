package com.ngnmsn.template.domain.auth;

import java.util.List;
import lombok.Data;
import org.jooq.types.ULong;

/**
 * AuthResultクラス
 */
@Data
public class AuthResult {

  String displayId;
  ULong userGroupId;
  String loginId;
  String password;
  String userName;
  List<String> permissionNames;
}
