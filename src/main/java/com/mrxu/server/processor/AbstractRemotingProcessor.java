package com.mrxu.server.processor;

import com.mrxu.common.utils.RemotingUtil;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.server.rpc.CommandFactory;
import com.mrxu.server.rpc.RemotingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public abstract class AbstractRemotingProcessor<T extends RemotingCommand> implements
        RemotingProcessor<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRemotingProcessor.class);
    private ExecutorService executor;
    private CommandFactory commandFactory;

    /**
     * Default constructor.
     */
    public AbstractRemotingProcessor() {

    }

    /**
     * Constructor.
     */
    public AbstractRemotingProcessor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    /**
     * Constructor.
     *
     * @param executor
     */
    public AbstractRemotingProcessor(ExecutorService executor) {
        this.executor = executor;
    }

    /**
     * Constructor.
     *
     * @param executor
     */
    public AbstractRemotingProcessor(CommandFactory commandFactory, ExecutorService executor) {
        this.commandFactory = commandFactory;
        this.executor = executor;
    }

    /**
     * Do the process.
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    public abstract void doProcess(RemotingContext ctx, T msg) throws Exception;

    /**
     * Process the remoting command with its own executor or with the defaultExecutor if its own if null.
     *
     * @param ctx
     * @param msg
     * @param defaultExecutor
     * @throws Exception
     */
    @Override
    public void process(RemotingContext ctx, T msg, ExecutorService defaultExecutor)
            throws Exception {
        ProcessTask task = new ProcessTask(ctx, msg);
        if (this.getExecutor() != null) {
            this.getExecutor().execute(task);
        } else {
            defaultExecutor.execute(task);
        }
    }

    /**
     * Getter method for property <tt>executor</tt>.
     *
     * @return property value of executor
     */
    @Override
    public ExecutorService getExecutor() {
        return executor;
    }

    /**
     * Setter method for property <tt>executor</tt>.
     *
     * @param executor value to be assigned to property executor
     */
    @Override
    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public void setCommandFactory(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    /**
     * Task for asynchronous process.
     *
     * @author jiangping
     * @version $Id: RemotingProcessor.java, v 0.1 2015-10-14 PM7:40:44 tao Exp $
     */
    class ProcessTask implements Runnable {

        RemotingContext ctx;
        T msg;

        public ProcessTask(RemotingContext ctx, T msg) {
            this.ctx = ctx;
            this.msg = msg;
        }

        /**
         * @see Runnable#run()
         */
        @Override
        public void run() {
            try {
                AbstractRemotingProcessor.this.doProcess(ctx, msg);
            } catch (Throwable e) {
                //protect the thread running this task
                String remotingAddress = RemotingUtil.parseRemoteAddress(ctx.getChannelContext()
                        .channel());
                logger
                        .error(
                                "Exception caught when process rpc request command in AbstractRemotingProcessor, Id="
                                        + msg.getId() + "! Invoke source address is [" + remotingAddress
                                        + "].", e);
            }
        }

    }

}
