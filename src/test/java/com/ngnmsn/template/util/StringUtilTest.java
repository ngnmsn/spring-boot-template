package com.ngnmsn.template.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StringUtilTest {

  @Autowired
  private StringUtil stringUtil;

  @DisplayName("generateUuid()の正常系テスト")
  @Test
  public void testGenerateUuidSuccess() {
    String result = stringUtil.generateUuid();
    assertThat(result.length(), is(32));
  }
}
