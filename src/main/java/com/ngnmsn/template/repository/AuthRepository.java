package com.ngnmsn.template.repository;

import com.ngnmsn.template.domain.auth.AuthResult;

import java.io.Serializable;
import java.util.Optional;

public interface AuthRepository extends Serializable {
    public AuthResult findByLoginId(String loginId);
}


