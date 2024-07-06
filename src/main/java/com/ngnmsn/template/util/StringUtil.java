package com.ngnmsn.template.util;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class StringUtil {

  public String generateUuid() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString().replace("-", "").toUpperCase();
  }
}