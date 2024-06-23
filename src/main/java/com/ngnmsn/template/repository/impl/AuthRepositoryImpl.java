package com.ngnmsn.template.repository.impl;

import com.ngnmsn.template.domain.auth.AuthResult;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.ngnmsn.template.Tables.USERS;

@Repository
public class AuthRepositoryImpl {
    @Autowired
    private DSLContext jooq;

    public AuthRepositoryImpl() {
        super();
    }

    public AuthResult findByLoginId(String loginId) {
        Record result = jooq.select()
                .from(USERS)
                .where(USERS.LOGIN_ID.eq(loginId))
                .fetchSingle();
        AuthResult authResult = new AuthResult();
        authResult.setDisplayId(result.getValue(USERS.DISPLAY_ID));
        authResult.setLoginId(result.getValue(USERS.LOGIN_ID));
        authResult.setPassword(result.getValue(USERS.PASSWORD));

        return authResult;
    }
}
