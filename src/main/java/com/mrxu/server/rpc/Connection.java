package com.mrxu.server.rpc;

import com.alibaba.fastjson2.JSON;
import com.mrxu.common.utils.RemotingUtil;
import com.mrxu.proxy.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class Connection {

    private static final Logger logger = LoggerFactory.getLogger(Connection.class);

    private Session session;

    private String poolKey;

    private String bizChannel;

    private Channel channel;

    private final ConcurrentHashMap<Long, InvokeFuture> invokeFutureMap = new ConcurrentHashMap<Long, InvokeFuture>(
            4);

    /**
     * Attribute key for connection
     */
    public static final AttributeKey<Connection> CONNECTION = AttributeKey.valueOf("connection");
    /**
     * Attribute key for heartbeat count
     */
    public static final AttributeKey<Integer> HEARTBEAT_COUNT = AttributeKey.valueOf("heartbeatCount");
    /**
     * Attribute key for protocol
     */
    public static final AttributeKey<Byte> PROTOCOL = AttributeKey.valueOf("protocol");

    private AtomicBoolean closed = new AtomicBoolean(false);

    private final ConcurrentHashMap<String/* attr key*/, Object /*attr value*/> attributes = new ConcurrentHashMap<String, Object>();

    /**
     * Constructor
     *
     * @param session
     */
    public Connection(Session session, String bizChannel) {
        this.bizChannel = bizChannel;
        this.session = session;
        this.poolKey = session.getSessionId();
        this.channel = session.getCtx().channel();
        this.channel.attr(CONNECTION).set(this);
    }

    public Connection(Session session, String poolKey, String bizChannel) {
        this(session, bizChannel);
        this.poolKey = poolKey;
    }

    /**
     * to check whether the connection is fine to use
     *
     * @return
     */
    public boolean isFine() {
        return this.channel != null && this.channel.isActive();
    }

    /**
     * Get the address of the remote peer.
     *
     * @return
     */
    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) this.channel.remoteAddress();
    }

    /**
     * Get the remote IP.
     *
     * @return
     */
    public String getRemoteIP() {
        return RemotingUtil.parseRemoteIP(this.channel);
    }

    /**
     * Get the remote port.
     *
     * @return
     */
    public int getRemotePort() {
        return RemotingUtil.parseRemotePort(this.channel);
    }

    /**
     * Get the address of the local peer.
     *
     * @return
     */
    public InetSocketAddress getLocalAddress() {
        return (InetSocketAddress) this.channel.localAddress();
    }

    /**
     * Get the local IP.
     *
     * @return
     */
    public String getLocalIP() {
        return RemotingUtil.parseLocalIP(this.channel);
    }

    /**
     * Get the local port.
     *
     * @return
     */
    public int getLocalPort() {
        return RemotingUtil.parseLocalPort(this.channel);
    }

    /**
     * Get the netty channel of the connection.
     *
     * @return
     */
    public Channel getChannel() {
        return this.channel;
    }

    public String getBizChannel() {
        return bizChannel;
    }

    public void setBizChannel(String bizChannel) {
        this.bizChannel = bizChannel;
    }

    /**
     * Get the InvokeFuture with invokeId of id.
     *
     * @param id
     * @return
     */
    public InvokeFuture getInvokeFuture(long id) {
        return this.invokeFutureMap.get(id);
    }

    /**
     * Add an InvokeFuture
     *
     * @param future
     * @return
     */
    public InvokeFuture addInvokeFuture(InvokeFuture future) {
        return this.invokeFutureMap.putIfAbsent(future.invokeId(), future);
    }

    /**
     * Remove InvokeFuture who's invokeId is id
     *
     * @param id
     * @return
     */
    public InvokeFuture removeInvokeFuture(long id) {
        return this.invokeFutureMap.remove(id);
    }

    /**
     * Do something when closing.
     */
    public void onClose() {
        Iterator<Entry<Long, InvokeFuture>> iter = invokeFutureMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Long, InvokeFuture> entry = iter.next();
            iter.remove();
            InvokeFuture future = entry.getValue();
            if (future != null) {
                future.tryAsyncExecuteInvokeCallbackAbnormally();
            }
        }
    }

    /**
     * Close the connection.
     */
    public void close() {
        if (closed.compareAndSet(false, true)) {
            try {
                if (this.getChannel() != null) {
                    this.channel.attr(CONNECTION).set(null);
                    this.getChannel().close().addListener(new ChannelFutureListener() {

                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (logger.isInfoEnabled()) {
                                logger.info("Close the connection to remote address={}, connection = {}, result={}, cause={}",
                                        RemotingUtil.parseRemoteAddress(Connection.this.getChannel()), JSON.toJSONString(Connection.this),
                                        future.isSuccess(), future.cause());
                            }
                        }

                    });
                }
            } catch (Exception e) {
                logger.warn("Exception caught when closing connection " +
                        RemotingUtil.parseRemoteAddress(Connection.this.getChannel()) + ", connection: " + JSON.toJSONString(Connection.this), e);
            }
        }
    }

    /**
     * Whether invokeFutures is completed
     */
    public boolean isInvokeFutureMapFinish() {
        return invokeFutureMap.isEmpty();
    }


    /**
     * Set attribute key=value.
     *
     * @param key
     * @param value
     */
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    /**
     * set attribute if key absent.
     *
     * @param key
     * @param value
     * @return
     */
    public Object setAttributeIfAbsent(String key, Object value) {
        return attributes.putIfAbsent(key, value);
    }

    /**
     * Remove attribute.
     *
     * @param key
     */
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    /**
     * Get attribute.
     *
     * @param key
     * @return
     */
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    /**
     * Clear attribute.
     */
    public void clearAttributes() {
        attributes.clear();
    }

    /**
     * Getter method for property <tt>invokeFutureMap</tt>.
     *
     * @return property value of invokeFutureMap
     */
    public ConcurrentHashMap<Long, InvokeFuture> getInvokeFutureMap() {
        return invokeFutureMap;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getPoolKey() {
        return poolKey;
    }

    public void setPoolKey(String poolKey) {
        this.poolKey = poolKey;
    }
}
