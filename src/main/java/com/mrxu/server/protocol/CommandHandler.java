package com.mrxu.server.protocol;

import com.mrxu.server.rpc.RemotingContext;

import java.util.concurrent.ExecutorService;

public interface CommandHandler {
    /**
     * Handle the command.
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    void handleCommand(RemotingContext ctx, Object msg) throws Exception;


    /**
     * Register default executor for the handler.
     *
     * @param executor
     */
    void registerDefaultExecutor(ExecutorService executor);

    /**
     * Get default executor for the handler.
     */
    ExecutorService getDefaultExecutor();

}