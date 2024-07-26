package com.mrxu.proxy.attributes;

import com.mrxu.proxy.session.Session;
import io.netty.util.AttributeKey;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/12/4 10:15
 */
public class Attributes {

    public static final AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

	public static final AttributeKey<Integer> PUSH_FAILURE_COUNT = AttributeKey.newInstance("failureCount");


	public static final AttributeKey<String> ORIGIN = AttributeKey.valueOf("origin");

	public static final AttributeKey<String> USER_AGENT = AttributeKey.valueOf("userAgent");

	public static final AttributeKey<String> TRANSPORT = AttributeKey.valueOf("transport");

}
