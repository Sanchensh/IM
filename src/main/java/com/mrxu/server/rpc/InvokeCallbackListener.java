package com.mrxu.server.rpc;

public interface InvokeCallbackListener {
    /**
     * Response arrived.
     *
     * @param future
     */
    void onResponse(final InvokeFuture future);

    /**
     * Get the remote address.
     *
     * @return
     */
    String getRemoteAddress();
}
