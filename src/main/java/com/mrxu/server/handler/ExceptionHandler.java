package com.mrxu.server.handler;

import com.mrxu.common.ResponseStatus;
import com.mrxu.common.utils.SnowflakeIDGenUtils;
import com.mrxu.remote.domain.response.ImResponseCommand;
import com.mrxu.server.rpc.CommandFactory;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: zhaoyi.wang
 * @data 2021/1/4 1:43 下午
 * @description:
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ExceptionHandler extends ChannelDuplexHandler {

    @Autowired
    private CommandFactory commandFactory;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        String errorMessage = ExceptionUtils.getRootCauseMessage(cause);
        log.error("catch exception: " + errorMessage, cause);
        ImResponseCommand imResponseCommand = commandFactory.createExceptionResponse(SnowflakeIDGenUtils.getId(), ResponseStatus.ERROR, cause);
        ctx.channel().writeAndFlush(imResponseCommand);
    }

}
