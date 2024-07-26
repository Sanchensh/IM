package com.mrxu.common.utils;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2019-01-11 14:02
 */
public class StringUtils {

    private static String UNDERLINE = "_";

    public static final String EMPTY = "";

    public static String compareAndConnect(String a, String b) {
        if (a.compareTo(b) <= 0) {
            return a + UNDERLINE + b;
        } else {
            return b + UNDERLINE + a;
        }
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !StringUtils.isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !StringUtils.isBlank(cs);
    }
}
