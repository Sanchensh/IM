package com.mrxu.server.rpc;

import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.server.utils.ConnectionUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class RemotingContext {

    private ChannelHandlerContext channelContext;

    private boolean serverSide = false;

    /**
     * whether need handle request timeout, if true, request will be discarded. The default value is true
     */
    private boolean timeoutDiscard = true;

    /**
     * request arrive time stamp
     */
    private long arriveTimestamp;

    /**
     * request timeout setting by invoke side
     */
    private int timeout;

    /**
     * rpc command type
     */
    private int rpcCommandType;

    /**
     * Constructor.
     *
     * @param ctx
     */
    public RemotingContext(ChannelHandlerContext ctx) {
        this.channelContext = ctx;
    }

    /**
     * Constructor.
     *
     * @param ctx
     * @param serverSide
     */
    public RemotingContext(ChannelHandlerContext ctx, boolean serverSide) {
        this.channelContext = ctx;
        this.serverSide = serverSide;
    }

    /**
     * Wrap the writeAndFlush method.
     *
     * @param msg
     * @return
     */
    public ChannelFuture writeAndFlush(RemotingCommand msg) {
        return this.channelContext.writeAndFlush(msg);
    }

    public ChannelFuture writeAndFlush(TextWebSocketFrame textWebSocketFrame) {
        return this.channelContext.writeAndFlush(textWebSocketFrame);
    }

    /**
     * whether this request already timeout
     *
     * @return
     */
    public boolean isRequestTimeout() {
        if (this.timeout > 0 && (System.currentTimeMillis() - this.arriveTimestamp) > this.timeout) {
            return true;
        }
        return false;
    }

    /**
     * The server side
     *
     * @return
     */
    public boolean isServerSide() {
        return this.serverSide;
    }


    /**
     * Get connection from channel
     *
     * @return
     */
    public Connection getConnection() {
        return ConnectionUtil.getConnectionFromChannel(channelContext.channel());
    }

    /**
     * Get the channel handler context.
     *
     * @return
     */
    public ChannelHandlerContext getChannelContext() {
        return channelContext;
    }

    /**
     * Set the channel handler context.
     *
     * @param ctx
     */
    public void setChannelContext(ChannelHandlerContext ctx) {
        this.channelContext = ctx;
    }

    /**
     * Setter method for property <tt>arriveTimestamp<tt>.
     *
     * @param arriveTimestamp value to be assigned to property arriveTimestamp
     */
    public void setArriveTimestamp(long arriveTimestamp) {
        this.arriveTimestamp = arriveTimestamp;
    }

    /**
     * Getter method for property <tt>arriveTimestamp</tt>.
     *
     * @return property value of arriveTimestamp
     */
    public long getArriveTimestamp() {
        return arriveTimestamp;
    }

    /**
     * Setter method for property <tt>timeout<tt>.
     *
     * @param timeout value to be assigned to property timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Getter method for property <tt>timeout</tt>.
     *
     * @return property value of timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Setter method for property <tt>rpcCommandType<tt>.
     *
     * @param rpcCommandType value to be assigned to property rpcCommandType
     */
    public void setRpcCommandType(int rpcCommandType) {
        this.rpcCommandType = rpcCommandType;
    }

    public boolean isTimeoutDiscard() {
        return timeoutDiscard;
    }

    public RemotingContext setTimeoutDiscard(boolean failFastEnabled) {
        this.timeoutDiscard = failFastEnabled;
        return this;
    }
}
