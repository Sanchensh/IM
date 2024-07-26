package com.mrxu.server.handler;

import com.mrxu.common.ConnectionEventType;
import com.mrxu.common.utils.NamedThreadFactory;
import com.mrxu.common.utils.RemotingUtil;
import com.mrxu.proxy.attributes.Attributes;
import com.mrxu.server.config.ConnectionLimiter;
import com.mrxu.server.rpc.Connection;
import com.mrxu.server.rpc.ConnectionEventListener;
import com.mrxu.server.rpc.ConnectionManager;
import io.netty.channel.*;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.mrxu.proxy.attributes.Attributes.TRANSPORT;


/**
 * @Description: 连接管理的netty handler
 * @author: ztowh
 * @Date: 2018-12-07 09:54
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class ImConnectionEventHandler extends ChannelDuplexHandler {

    @Autowired
    private ConnectionManager connectionManager;
    @Autowired
    private ConnectionEventListener eventListener;
    @Autowired
    private ConnectionLimiter connectionLimiter;

    private ConnectionEventExecutor eventExecutor;

    private final static long defaultTickDuration = 10;

    static final Timer TIMER = new HashedWheelTimer(new NamedThreadFactory(
            "CheckConnection" + defaultTickDuration, true),
            defaultTickDuration, TimeUnit.MILLISECONDS);

    /**
     * @see ChannelDuplexHandler#connect(ChannelHandlerContext, SocketAddress, SocketAddress, ChannelPromise)
     */
    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
                        SocketAddress localAddress, ChannelPromise promise) throws Exception {
        if (log.isInfoEnabled()) {
            final String local = localAddress == null ? null : RemotingUtil
                    .parseSocketAddressToString(localAddress);
            final String remote = remoteAddress == null ? "UNKNOWN" : RemotingUtil
                    .parseSocketAddressToString(remoteAddress);
            if (local == null) {
                if (log.isInfoEnabled()) {
                    log.info("Try connect to {}", remote);
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Try connect from {} to {}", local, remote);
                }
            }
        }

        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    /**
     * @see ChannelDuplexHandler#disconnect(ChannelHandlerContext, ChannelPromise)
     */
    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        infoLog("Connection disconnect to {}", RemotingUtil.parseRemoteAddress(ctx.channel()));
        super.disconnect(ctx, promise);
    }

    /**
     * @see ChannelDuplexHandler#close(ChannelHandlerContext, ChannelPromise)
     */
    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
//        final Connection conn = ctx.channel().attr(Connection.CONNECTION).get();
//        if (conn != null) {
//            log.info("Connection closed: {} sessionId {}", RemotingUtil.parseRemoteAddress(ctx.channel()), conn.getSession() != null ? conn.getSession().getSessionId() : "");
//            String transport = ctx.channel().attr(TRANSPORT).get();
//			// long poll类型需要超时判断，remove
//			if(transport == null) {
//				conn.onClose();
//				// 连接移除|session
//				connectionManager.remove(conn, ctx);
//			}
//        }
        super.close(ctx, promise);
    }

    /**
     * @see ChannelInboundHandlerAdapter#channelRegistered(ChannelHandlerContext)
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        infoLog("Connection channel registered: {}", RemotingUtil.parseRemoteAddress(ctx.channel()));
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        infoLog("Connection channel unregistered: {}",
                RemotingUtil.parseRemoteAddress(ctx.channel()));
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        infoLog("Connection channel active: {}", RemotingUtil.parseRemoteAddress(ctx.channel()));
        if (!connectionLimiter.canAccept()) {
            ctx.close();
        } else {
            TIMER.newTimeout(timeout -> {
                // 1秒内仍未提交认证请求则关闭链接
                if (!ctx.channel().hasAttr(Attributes.SESSION)) {
                    log.warn("检测到长时间未提交认证请求，关闭连接.");
                    ctx.close();
                }
            }, 100000, TimeUnit.MILLISECONDS);
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Connection conn = ctx.channel().attr(Connection.CONNECTION).get();
        if (conn != null) {
            String transport = ctx.channel().attr(TRANSPORT).get();
            // long poll类型需要超时判断，remove
            if (transport == null) {
                this.getConnectionManager().remove(conn, ctx);
            }
            // trigger close connection event
            onEvent(conn, ConnectionEventType.CLOSE);
            log.info("Connection channel inactive: {}", conn.getSession());
        }
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event instanceof ConnectionEventType) {
            switch ((ConnectionEventType) event) {
                case CONNECT:
                    Channel channel = ctx.channel();
                    if (null != channel) {
                        Connection connection = channel.attr(Connection.CONNECTION).get();
                        this.onEvent(connection, ConnectionEventType.CONNECT);
                    } else {
                        log
                                .warn("channel null when handle user triggered event in ConnectionEventHandler!");
                    }
                    break;
                default:
                    return;
            }
        } else {
            super.userEventTriggered(ctx, event);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        final String remoteAddress = RemotingUtil.parseRemoteAddress(ctx.channel());
        final String localAddress = RemotingUtil.parseLocalAddress(ctx.channel());
        log
                .warn(
                        "ExceptionCaught in connection: local[{}], remote[{}], close the connection! Cause[{}:{}]",
                        localAddress, remoteAddress, cause.getClass().getSimpleName(), cause);
        ctx.channel().close();
    }

    /**
     * @param conn
     * @param type
     */
    private void onEvent(final Connection conn, final ConnectionEventType type) {
        if (this.eventListener != null) {
            this.eventExecutor.onEvent(new Runnable() {
                @Override
                public void run() {
                    ImConnectionEventHandler.this.eventListener.onEvent(type, conn);
                }
            });
        }
    }

    /**
     * Getter method for property <tt>listener</tt>.
     *
     * @return property value of listener
     */
    public ConnectionEventListener getConnectionEventListener() {
        return eventListener;
    }

    /**
     * Setter method for property <tt>listener</tt>.
     *
     * @param listener value to be assigned to property listener
     */
    public void setConnectionEventListener(ConnectionEventListener listener) {
        if (listener != null) {
            this.eventListener = listener;
            if (this.eventExecutor == null) {
                this.eventExecutor = new ConnectionEventExecutor();
            }
        }
    }

    /**
     * Getter method for property <tt>connectionManager</tt>.
     *
     * @return property value of connectionManager
     */
    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    /**
     * Setter method for property <tt>connectionManager</tt>.
     *
     * @param connectionManager value to be assigned to property connectionManager
     */
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    /**
     * Dispatch connection event.
     *
     * @author jiangping
     * @version $Id: ConnectionEventExecutor.java, v 0.1 Mar 4, 2016 9:20:15 PM tao Exp $
     */
    @Slf4j
    public static class ConnectionEventExecutor {
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10000),
                new NamedThreadFactory("Bolt-conn-event-executor", true));

        /**
         * Process event.
         *
         * @param event
         */
        public void onEvent(Runnable event) {
            try {
                executor.execute(event);
            } catch (Throwable t) {
                log.error("Exception caught when execute connection event!", t);
            }
        }
    }

    /**
     * print info log
     *
     * @param format
     * @param addr
     */
    private void infoLog(String format, String addr) {
        if (log.isInfoEnabled()) {
            if (StringUtils.isNotEmpty(addr)) {
                log.info(format, addr);
            } else {
                log.info(format, "UNKNOWN-ADDR");
            }
        }
    }
}
