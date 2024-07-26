package com.mrxu.server.rpc;

public interface ConnectionEventProcessor {
    /**
     * Process event.<br>
     *
     * @param conn
     */
    void onEvent(Connection conn);
}
