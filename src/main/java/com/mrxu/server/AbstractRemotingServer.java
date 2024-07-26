package com.mrxu.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractRemotingServer implements RemotingServer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractRemotingServer.class);

    private AtomicBoolean started = new AtomicBoolean(false);
    private String ip;
    private int port;

    public AbstractRemotingServer(int port) {
        this(new InetSocketAddress(port).getAddress().getHostAddress(), port);
    }

    public AbstractRemotingServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public boolean start() {
        if (started.compareAndSet(false, true)) {
            try {
                doInit();

                logger.warn("Prepare to start server on port {} ", port);
                if (doStart()) {
                    logger.warn("Server started on port {}", port);
                    return true;
                } else {
                    logger.warn("Failed starting server on port {}", port);
                    return false;
                }
            } catch (Throwable t) {
                this.stop();// do stop to ensure close resources created during doInit()
                throw new IllegalStateException("ERROR: Failed to start the Server!", t);
            }
        } else {
            String errMsg = "ERROR: The server has already started!";
            logger.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
    }

    @Override
    public boolean stop() {
        if (started.compareAndSet(true, false)) {
            return this.doStop();
        } else {
            throw new IllegalStateException("ERROR: The server has already stopped!");
        }
    }

    @Override
    public String ip() {
        return ip;
    }

    @Override
    public int port() {
        return port;
    }

    protected abstract void doInit();

    protected abstract boolean doStart() throws InterruptedException;

    protected abstract boolean doStop();

}
