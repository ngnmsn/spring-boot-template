package com.ngnmsn.template.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class StringUtilTest {
    @Autowired
    private StringUtil stringUtil;

    @DisplayName("generateUUID()の正常系テスト")
    @Test
    public void testGenerateUUIDSuccess() {
        String result = stringUtil.generateUUID();
        assertThat(result.length(), is(32));
    }
}
