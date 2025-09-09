package com.ngnmsn.template.domain.service;

import com.ngnmsn.template.domain.model.auth.AuthResult;
import com.ngnmsn.template.domain.model.auth.LoginUserDetails;
import com.ngnmsn.template.infrastructure.repository.jooq.sample.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * LoginUserDetailServiceクラス
 */
@Service
public class LoginUserDetailService implements UserDetailsService {

  @Autowired
  private AuthRepository authRepository;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    AuthResult result = authRepository.findByLoginId(loginId);
    return new LoginUserDetails(result);
  }
}
