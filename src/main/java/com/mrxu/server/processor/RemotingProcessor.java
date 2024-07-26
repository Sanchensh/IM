package com.mrxu.server.processor;

import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.server.rpc.RemotingContext;

import java.util.concurrent.ExecutorService;

public interface RemotingProcessor<T extends RemotingCommand> {

    /**
     * Process the remoting command.
     *
     * @param ctx
     * @param msg
     * @param defaultExecutor
     * @throws Exception
     */
    void process(RemotingContext ctx, T msg, ExecutorService defaultExecutor) throws Exception;

    /**
     * Get the executor.
     *
     * @return
     */
    ExecutorService getExecutor();

    /**
     * Set executor.
     *
     * @param executor
     */
    void setExecutor(ExecutorService executor);

}
