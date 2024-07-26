package com.mrxu.server.rpc;

import com.mrxu.remote.domain.RemotingCommand;

public interface InvokeFuture {
    /**
     * Wait response with timeout.
     *
     * @param timeoutMillis time out in millisecond
     * @return remoting command
     * @throws InterruptedException if interrupted
     */
    RemotingCommand waitResponse(final long timeoutMillis) throws InterruptedException;

    /**
     * Wait response with unlimit timeout
     *
     * @return remoting command
     * @throws InterruptedException if interrupted
     */
    RemotingCommand waitResponse() throws InterruptedException;

    /**
     * Put the response to the future.
     *
     * @param response remoting command
     */
    void putResponse(final RemotingCommand response);

    /**
     * Get the id of the invocation.
     *
     * @return invoke id
     */
    long invokeId();

    /**
     * Execute the callback.
     */
    void executeInvokeCallback();

    /**
     * Asynchronous execute the callback abnormally.
     */
    void tryAsyncExecuteInvokeCallbackAbnormally();

    /**
     * Set the cause if exception caught.
     */
    void setCause(Throwable cause);

    /**
     * Get the cause of exception of the future.
     *
     * @return the cause
     */
    Throwable getCause();

    /**
     * Whether the future is done.
     *
     * @return true if the future is done
     */
    boolean isDone();

    /**
     * Get application classloader.
     *
     * @return application classloader
     */
    ClassLoader getAppClassLoader();

    /**
     * Get the protocol code of command.
     *
     * @return protocol code
     */
    byte getProtocolCode();

    int increRetryTimes();
}
