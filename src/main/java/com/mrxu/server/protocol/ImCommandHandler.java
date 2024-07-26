package com.mrxu.server.protocol;

import com.mrxu.common.ResponseStatus;
import com.mrxu.remote.domain.ImCommand;
import com.mrxu.remote.domain.request.ImRequestCommand;
import com.mrxu.remote.domain.response.ImResponseCommand;
import com.mrxu.server.processor.BizProcessorManager;
import com.mrxu.server.processor.RemotingProcessor;
import com.mrxu.server.rpc.CommandFactory;
import com.mrxu.server.rpc.RemotingContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/23 18:20
 */
@Component
public class ImCommandHandler implements CommandHandler {

    private Logger logger = LoggerFactory.getLogger(ImCommandHandler.class);

    @Autowired
    BizProcessorManager bizProcessorManager;

    @Autowired
    CommandFactory commandFactory;

    public ImCommandHandler() {

    }

    @Override
    public void handleCommand(RemotingContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof List) {
                final Runnable handleTask = new Runnable() {
                    @Override
                    public void run() {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Batch message! size={}", ((List<?>) msg).size());
                        }
                        for (final Object m : (List<?>) msg) {
                            ImCommandHandler.this.process(ctx, m);
                        }
                    }
                };
                // 替换成配置
                if (true) {
                    // If msg is list ,then the batch submission to biz threadpool can save io thread.
                    // See com.alipay.remoting.decoder.ProtocolDecoder
                    bizProcessorManager.getDefaultExecutor().execute(handleTask);
                } else {
                    handleTask.run();
                }
            } else {
                process(ctx, msg);
            }
        } catch (final Throwable t) {
            processException(ctx, msg, t);
        }
    }

    private void process(RemotingContext ctx, Object msg) {
        try {
            final ImCommand cmd = (ImCommand) msg;
            final RemotingProcessor processor = bizProcessorManager.getProcessor(cmd.getType());
            processor.process(ctx, cmd, bizProcessorManager.getDefaultExecutor());
        } catch (final Throwable t) {
            processException(ctx, msg, t);
        }
    }

    private void processException(RemotingContext ctx, Object msg, Throwable t) {
        if (msg instanceof List) {
            for (final Object m : (List<?>) msg) {
                processExceptionForSingleCommand(ctx, m, t);
            }
        } else {
            processExceptionForSingleCommand(ctx, msg, t);
        }
    }

    /*
     * Return error command if necessary.
     */
    private void processExceptionForSingleCommand(RemotingContext ctx, Object msg, Throwable t) {
        final long id = ((ImCommand) msg).getId();
        final String emsg =
                "Exception caught when processing " + ((msg instanceof ImCommand) ? "request, id=" : "response, id=");
        logger.warn(emsg + id, t);
        if (msg instanceof ImRequestCommand) {
            final ImRequestCommand cmd = (ImRequestCommand) msg;
            if (t instanceof RejectedExecutionException) {
                final ImResponseCommand response = this.commandFactory
                        .createExceptionResponse(id, ResponseStatus.SERVER_THREADPOOL_BUSY);
                // RejectedExecutionException here assures no response has been sent back
                // Other exceptions should be processed where exception was caught, because here we don't known whether ack had been sent back.
                ctx.getChannelContext().writeAndFlush(response).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            if (logger.isInfoEnabled()) {
                                logger.info("Write back exception response done, requestId={}, status={}", id,
                                        response.getResponseStatus());
                            }
                        } else {
                            logger.error("Write back exception response failed, requestId={}", id, future.cause());
                        }
                    }

                });
            }
        }
    }

    @Override
    public void registerDefaultExecutor(ExecutorService executor) {
        this.bizProcessorManager.registerDefaultExecutor(executor);
    }

    @Override
    public ExecutorService getDefaultExecutor() {
        return this.bizProcessorManager.getDefaultExecutor();
    }

}
