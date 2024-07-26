package com.mrxu.server;

import com.mrxu.common.utils.NamedThreadFactory;
import com.mrxu.common.utils.NettyEventLoopUtil;
import com.mrxu.remote.ZimConfiguration;
import com.mrxu.server.handler.AuthorizeHandler;
import com.mrxu.server.handler.ExceptionHandler;
import com.mrxu.server.handler.ImConnectionEventHandler;
import com.mrxu.server.handler.PortUnificationServerHandler;
import com.mrxu.server.handler.http.PollingHandler;
import com.mrxu.server.handler.ws.MessageEncoder;
import com.mrxu.server.handler.ws.WsHandler;
import com.mrxu.server.registry.RegistryService;
import com.mrxu.server.rpc.ConnectionEventListener;
import com.mrxu.server.rpc.ImConnectionManager;
import com.mrxu.server.utils.SslContextHelper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.net.ssl.SSLEngine;
import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/22 14:55
 */
@Component
public class ZimServer extends AbstractRemotingServer {

    private Logger logger = LoggerFactory.getLogger(ZimServer.class);

    /**
     * server bootstrap
     */
    private ServerBootstrap bootstrap;

    /**
     * channelFuture
     */
    private ChannelFuture channelFuture;

    /**
     * connection event handler
     */
    @Autowired
    private ImConnectionEventHandler connectionEventHandler;
    @Autowired
    private ConfigMananger configMananger;

    /**
     * connection event listener
     */
    private ConnectionEventListener connectionEventListener = new ConnectionEventListener();

    /**
     * boss event loop group, boss group should not be daemon, need shutdown manually
     */
    private final EventLoopGroup bossGroup = NettyEventLoopUtil.newEventLoopGroup(1, new NamedThreadFactory("Rpc-netty-server-boss", false));
    /**
     * worker event loop group. Reuse I/O worker threads between rpc servers.
     */
    private static final EventLoopGroup workerGroup = NettyEventLoopUtil.newEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2, new NamedThreadFactory("Rpc-netty-server-worker", true));

    /**
     * connection manager
     */
    @Autowired
    private ImConnectionManager connectionManager;
    @Autowired
    private RegistryService registryService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ZimConfiguration zimConfiguration;

    private final ScheduledExecutorService globalTrafficShapingExecutor = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("global-traffic-shaping"));

    private GlobalTrafficShapingHandler globalTrafficShapingHandler = new GlobalTrafficShapingHandler(globalTrafficShapingExecutor, 60000);

    private int port;
    @Autowired
    private AuthorizeHandler authorizeHandler;
    @Autowired
    private MessageEncoder messageEncoder;
    @Autowired
    private WsHandler wsHandler;
    @Autowired
    private PollingHandler pollingHandler;
    @Autowired
    private ExceptionHandler exceptionHandler;

    public ZimServer(@Value("${zim.port}") int port) {
        super(port);
    }

    @Override
    protected void doInit() {
        if (workerGroup instanceof NioEventLoopGroup) {
            ((NioEventLoopGroup) workerGroup).setIoRatio(configMananger.getNetty_io_ratio());
        } else if (workerGroup instanceof EpollEventLoopGroup) {
            ((EpollEventLoopGroup) workerGroup).setIoRatio(configMananger.getNetty_io_ratio());
        }

        this.connectionEventHandler.setConnectionEventListener(this.connectionEventListener);
        registryService.init();
        this.bootstrap = new ServerBootstrap();
        this.bootstrap.group(bossGroup, workerGroup).channel(NettyEventLoopUtil.getServerSocketChannelClass()).option(ChannelOption.SO_BACKLOG, configMananger.getTcp_so_backlog()).option(ChannelOption.SO_REUSEADDR, configMananger.isTcp_so_reuseaddr()).option(ChannelOption.SO_RCVBUF, 1024 * 256).option(ChannelOption.SO_SNDBUF, 1024 * 256).childOption(ChannelOption.TCP_NODELAY, configMananger.isTcp_nodelay()).childOption(ChannelOption.SO_KEEPALIVE, configMananger.isTcp_so_keepalive());

        // set write buffer water mark
        initWriteBufferWaterMark();


        // init byte buf allocator
        if (configMananger.isNetty_buffer_pooled()) {
            this.bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT).childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        } else {
            this.bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT).childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
        }

        // enable trigger mode for epoll if need
        NettyEventLoopUtil.enableTriggeredMode(bootstrap);

        this.bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel channel) {
                ChannelPipeline pipeline = channel.pipeline();
                if (configMananger.isEnableSSL()) {
                    SslContext sslContext = null;
                    try {
                        sslContext = SslContextHelper.getSslContext();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SSLEngine engine = sslContext.newEngine(channel.alloc());
                    engine.setUseClientMode(false);
                    // 选择需要启用的 SSL 协议，如 SSLv2 SSLv3 TLSv1 TLSv1.1 TLSv1.2 等
                    engine.setEnabledProtocols(engine.getSupportedProtocols());
                    // 选择需要启用的 CipherSuite 组合，如 ECDHE-ECDSA-CHACHA20-POLY1305 等
                    engine.setEnabledCipherSuites(engine.getSupportedCipherSuites());
                    pipeline.addLast("ssl", new SslHandler(engine));
                }
                pipeline.addLast(globalTrafficShapingHandler);
                PortUnificationServerHandler portUnificationServerHandler = new PortUnificationServerHandler();
                portUnificationServerHandler.setAuthorizeHandler(authorizeHandler);
                portUnificationServerHandler.setMessageEncoder(messageEncoder);
                portUnificationServerHandler.setPollingHandler(pollingHandler);
                portUnificationServerHandler.setWsHandler(wsHandler);
                portUnificationServerHandler.setExceptionHandler(exceptionHandler);
                pipeline.addLast("unification", portUnificationServerHandler);
                pipeline.addLast("connectionEventHandler", connectionEventHandler);
            }

        });
        // 堆外内存的监控
        DirectMemoryReporter.getInstance().startReport();
    }

    @PostConstruct
    @Override
    public boolean start() {
        return super.start();
    }

    @PreDestroy
    @Override
    public boolean stop() {
        return super.stop();
    }

    @Override
    protected boolean doStart() throws InterruptedException {
        this.channelFuture = this.bootstrap.bind(new InetSocketAddress(ip(), port())).sync();
        return this.channelFuture.isSuccess();
    }

    @Override
    protected boolean doStop() {
        if (null != this.channelFuture) {
            this.channelFuture.channel().close();
        }
//        if (this.switches().isOn(GlobalSwitch.SERVER_SYNC_STOP)) {
        this.bossGroup.shutdownGracefully().awaitUninterruptibly();
//        } else {
//            this.bossGroup.shutdownGracefully();
//        }
        this.connectionManager.removeAll();
        logger.warn("Zim Server stopped!");
        return true;
    }

    /**
     * init netty write buffer water mark
     */
    private void initWriteBufferWaterMark() {
        int lowWaterMark = configMananger.getNetty_buffer_low_watermark();
        int highWaterMark = configMananger.getNetty_buffer_high_watermark();

        if (lowWaterMark > highWaterMark) {
            throw new IllegalArgumentException(String.format("[server side] netty high water mark {%s} should not be smaller than low water mark {%s} bytes)", highWaterMark, lowWaterMark));
        } else {
            logger.warn("[server side] netty low water mark is {} bytes, high water mark is {} bytes", lowWaterMark, highWaterMark);
        }
        this.bootstrap.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(lowWaterMark, highWaterMark));
    }

    public GlobalTrafficShapingHandler getGlobalTrafficShapingHandler() {
        return globalTrafficShapingHandler;
    }
}
