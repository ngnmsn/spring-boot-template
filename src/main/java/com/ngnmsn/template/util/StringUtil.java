package com.ngnmsn.template.util;

import java.util.UUID;

/**
 * StringUtilクラス
 */
public final class StringUtil {

    private StringUtil() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static String generateUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").toUpperCase();
    }
}