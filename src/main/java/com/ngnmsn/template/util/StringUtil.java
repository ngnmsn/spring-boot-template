package com.ngnmsn.template.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringUtil {
    public String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").toUpperCase();
    }
}