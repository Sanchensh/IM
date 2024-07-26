package com.mrxu.server;

public interface RemotingServer {

    /**
     * Start the server.
     */
    boolean start();

    /**
     * Stop the server.
     * <p>
     * Remoting server can not be used any more after stop.
     * If you need, you should destroy it, and instantiate another one.
     */
    boolean stop();

    /**
     * Get the ip of the server.
     *
     * @return ip
     */
    String ip();

    /**
     * Get the port of the server.
     *
     * @return listened port
     */
    int port();


}
