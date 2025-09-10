package com.ngnmsn.template.infrastructure.adapter.security;

import com.ngnmsn.template.domain.model.auth.AuthResult;
import com.ngnmsn.template.infrastructure.repository.jooq.sample.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Securityとドメインモデルを繋ぐアダプター
 * 
 * <p>Spring SecurityのUserDetailsServiceインターフェースを実装し、
 * ドメインモデルのAuthResultをSpring Security用のUserDetailsに変換します。
 * 
 * <p>このクラスはinfrastructure層のアダプターとして、
 * 外部フレームワーク(Spring Security)とドメイン層を繋ぐ責務を持ちます。
 */
@Service
public class SpringSecurityUserDetailsAdapter implements UserDetailsService {

  @Autowired
  private AuthRepository authRepository;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    AuthResult result = authRepository.findByLoginId(loginId);
    return new SpringSecurityUserDetails(result);
  }
}
