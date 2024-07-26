package com.mrxu.proxy.session;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

/**
 * @Description: session管理
 * @author: ztowh
 * @Date: 2019-01-02 17:14
 */
public interface SessionManager {

	Session createAndSaveSession(String sessionId, String bizChannel, ChannelHandlerContext ctx, Map<String, Object> extraMap);

	Session updateSession(String sessionId, String bizChannel,ChannelHandlerContext ctx, Map<String, Object> extraMap);

	void removeSession(Session session, String bizChannel);

	void removeSession(String sessionId, String bizChannel);

	void touchSession(String sessionId);

}
