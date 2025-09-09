package com.ngnmsn.template.infrastructure.repository.jooq.sample.jooq.sample;

import static com.ngnmsn.template.Tables.PERMISSIONS;
import static com.ngnmsn.template.Tables.ROLES;
import static com.ngnmsn.template.Tables.ROLES_PERMISSIONS;
import static com.ngnmsn.template.Tables.USERS;
import static com.ngnmsn.template.Tables.USER_GROUPS;

import com.ngnmsn.template.domain.model.auth.AuthResult;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * AuthRepositoryImplクラス
 */
@Repository
public class AuthRepository {

  @Autowired
  private DSLContext jooq;

  /**
   * findByLoginIdメソッド
   *
   * @param loginId ログインID
   * @return AuthResult ログインIDで検索したユーザ情報
   */
  public AuthResult findByLoginId(String loginId) {
    Record result = jooq.select()
        .from(USERS)
        .where(USERS.LOGIN_ID.eq(loginId))
        .fetchSingle();
    AuthResult authResult = new AuthResult();
    authResult.setDisplayId(result.getValue(USERS.DISPLAY_ID));
    authResult.setUserGroupId(result.getValue(USERS.USER_GROUP_ID));
    authResult.setLoginId(result.getValue(USERS.LOGIN_ID));
    authResult.setPassword(result.getValue(USERS.PASSWORD));

    Result<Record> results = jooq.select()
        .from(USER_GROUPS
            .join(ROLES).on(ROLES.ID.eq(USER_GROUPS.ID))
            .join(ROLES_PERMISSIONS).on(ROLES_PERMISSIONS.ROLE_ID.eq(ROLES.ID))
            .join(PERMISSIONS).on(PERMISSIONS.ID.eq(ROLES_PERMISSIONS.PERMISSION_ID)))
        .where(USER_GROUPS.ID.eq(authResult.getUserGroupId()))
        .fetch();
    List<String> permissionNames = new ArrayList<String>();
    for (Record r : results) {
      permissionNames.add(r.getValue(PERMISSIONS.PERMISSION_NAME));
    }
    authResult.setPermissionNames(permissionNames);

    return authResult;
  }
}
