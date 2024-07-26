package com.mrxu.server.processor;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.Command;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.server.rpc.RemotingContext;

/**
 * @Description: 业务command处理器
 * @author: ztowh
 * @Date: 2018/12/4 11:04
 */
public interface CommandProcessor<T extends Command> {

    ImCommandCode getCmdCode();

    Object handle(RemotingContext ctx, T cmd, RemotingCommand remotingCommand);

}
