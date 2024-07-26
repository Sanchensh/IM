package com.mrxu.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class DigestUtil {

    public static final String GBK = "GBK";
    public static final String UTF8 = "UTF-8";

    public static String doSign(String content, String keys, String charset) {
        String sign = "";
        try {
            String data = content + keys;
            byte[] dataBytes = DigestUtils.md5(data.getBytes(charset));
            sign = new String(Base64.encodeBase64(dataBytes), charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sign;
    }

}
