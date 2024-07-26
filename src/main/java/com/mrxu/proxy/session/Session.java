package com.mrxu.proxy.session;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @Description: 用户的会话类
 * @author: ztowh
 * @Date: 2018/12/3 16:17
 */
@Data
@AllArgsConstructor
public class Session {

    private String sessionId;

    private ChannelHandlerContext ctx;

    private Map<String, Object> extraMap;
}
