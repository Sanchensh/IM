package com.mrxu.proxy.session;

import java.util.UUID;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/12/4 09:20
 */
public class IdGenerator {

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
