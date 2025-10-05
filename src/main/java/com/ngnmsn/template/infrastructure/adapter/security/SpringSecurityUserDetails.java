package com.ngnmsn.template.infrastructure.adapter.security;

import com.ngnmsn.template.domain.model.auth.AuthResult;
import java.util.Collection;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Spring Security用のUserDetails実装
 * 
 * <p>ドメインモデルのAuthResultをSpring SecurityのUserDetailsに変換するアダプター。
 * infrastructure層のアダプターとして、外部フレームワークとドメインモデルを繋ぎます。
 * 
 * <p>このクラスは以下の変換を行います：
 * <ul>
 *   <li>AuthResult → UserDetails</li>
 *   <li>PermissionNames → GrantedAuthority</li>
 *   <li>ドメインの認証情報をSpring Security形式に変換</li>
 * </ul>
 */
@EqualsAndHashCode
public class SpringSecurityUserDetails implements UserDetails {

  private final String displayId;
  private final String loginId;
  private final String password;
  private final String userName;
  private final Collection<? extends GrantedAuthority> authorities;

  /**
   * ドメインモデルからSpring Security用オブジェクトを構築
   *
   * @param authResult ドメインの認証結果
   */
  public SpringSecurityUserDetails(AuthResult authResult) {
    this.displayId = authResult.getDisplayId();
    this.loginId = authResult.getLoginId();
    this.password = authResult.getPassword();
    this.userName = authResult.getUserName();
    this.authorities = authResult.getPermissionNames()
        .stream()
        .map(permissionName -> new SimpleGrantedAuthority(permissionName))
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
