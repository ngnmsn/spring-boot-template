package com.ngnmsn.template.service;

import com.ngnmsn.template.domain.auth.AuthResult;
import com.ngnmsn.template.domain.auth.LoginUserDetails;
import com.ngnmsn.template.repository.impl.AuthRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailService implements UserDetailsService {

  @Autowired
  private AuthRepositoryImpl authRepositoryImpl;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    AuthResult result = authRepositoryImpl.findByLoginId(loginId);
    LoginUserDetails loginUserDetails = new LoginUserDetails(result);
    return loginUserDetails;
  }
}
