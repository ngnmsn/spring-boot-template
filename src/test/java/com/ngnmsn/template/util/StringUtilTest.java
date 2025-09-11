package com.ngnmsn.template.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * StringUtilTestクラス
 */
public class StringUtilTest {

  @DisplayName("generateUuid()の正常系テスト")
  @Test
  public void testGenerateUuidSuccess() {
    String result = StringUtil.generateUuid();
    assertThat(result.length(), is(32));
  }
}
