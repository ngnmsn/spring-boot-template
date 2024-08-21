package com.ngnmsn.template.domain.service;

import com.ngnmsn.template.domain.model.auth.AuthResult;
import com.ngnmsn.template.domain.model.auth.LoginUserDetails;
import com.ngnmsn.template.repository.impl.AuthRepositoryImpl;
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
  private AuthRepositoryImpl authRepositoryImpl;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    AuthResult result = authRepositoryImpl.findByLoginId(loginId);
    return new LoginUserDetails(result);
  }
}
