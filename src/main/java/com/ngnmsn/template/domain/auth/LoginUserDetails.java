package com.ngnmsn.template.domain.auth;

import java.util.Arrays;
import java.util.Collection;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * LoginUserDetailsクラス
 */
@EqualsAndHashCode
public class LoginUserDetails implements UserDetails {

  private final String displayId;
  private final String loginId;
  private final String password;
  private final String userName;
  private final Collection<? extends GrantedAuthority> authorities;

  /**
   * LoginUserDetailsコンストラクタ
   *
   * @param authResult ユーザ情報
   */
  public LoginUserDetails(AuthResult authResult) {
    this.displayId = authResult.getDisplayId();
    this.loginId = authResult.getLoginId();
    this.password = authResult.getPassword();
    this.userName = authResult.getUserName();
    this.authorities = Arrays.stream("admin".split(","))
        .map(role -> new SimpleGrantedAuthority(role))
        .toList();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.loginId;
  }

  public String getDisplayId() {
    return this.displayId;
  }

  public String getName() {
    return this.userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    //  パスワードが期限切れでなければtrueを返す
    return true;
  }

  @Override
  public boolean isEnabled() {
    //  ユーザーが有効ならtrueを返す
    return true;
  }
}
