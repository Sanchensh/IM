package com.mrxu.server.rpc;


import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mrxu.common.domain.biz.UserEvent;
import com.mrxu.common.enums.UserEventType;
import com.mrxu.common.exception.ConnectionInvalidException;
import com.mrxu.common.exception.ConnectionNotWritableException;
import com.mrxu.event.common.ConnectionCloseEvent;
import com.mrxu.event.common.ConnectionEstablishEvent;
import com.mrxu.event.interfaces.service.EventPublisherService;
import com.mrxu.proxy.session.Session;
import com.mrxu.proxy.session.SessionManager;
import com.mrxu.remote.ZimConfiguration;
import com.mrxu.remote.converter.ConnectionCloseEventConverter;
import com.mrxu.remote.converter.ConnectionEstablishEventConverter;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.service.MessageSendService;
import com.mrxu.server.ConfigMananger;
import com.mrxu.server.handler.ImConnectionEventHandler;
import com.mrxu.server.registry.RegistryService;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.mrxu.proxy.attributes.Attributes.PUSH_FAILURE_COUNT;
import static com.mrxu.proxy.attributes.Attributes.TRANSPORT;
import static com.mrxu.proxy.utils.NetUtils.NODE_ADDRESS;

/**
 * @Description: 连接管理器
 * @author: ztowh
 * @Date: 2018-12-07 17:36
 */
@Component
public class ImConnectionManager implements ConnectionManager, Scannable {

    // ~~~ constants
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ImConnectionManager.class);

    /**
     * connection pool initialize tasks
     */
    protected ConcurrentHashMap<String, Map<String, Connection>> connectionMap;

    /**
     * connection event handler
     */
    @Autowired
    protected ImConnectionEventHandler connectionEventHandler;

    /**
     * connection event listener
     */
    protected ConnectionEventListener connectionEventListener;

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private RegistryService registryService;

    @Autowired
    private MessageSendService messageSendService;
    @Autowired
    private EventPublisherService eventPublisherService;
    @Autowired
    private ConnectionEstablishEventConverter connectionEstablishEventConverter;
    @Autowired
    private ConnectionCloseEventConverter connectionCloseEventConverter;
    @Autowired
    private ConfigMananger configMananger;
    @Autowired
    private ZimConfiguration zimConfiguration;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private LoadingCache<Long, AtomicInteger> msgSendFailureCounter;


    // ~~~ constructors

    /**
     * Default constructor
     */
    public ImConnectionManager() {
        this.connectionMap = new ConcurrentHashMap<String, Map<String, Connection>>();
//        this.msgSendFailureCounter = new ConcurrentHashMap<>();
        this.msgSendFailureCounter = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).maximumSize(1000000).build(new CacheLoader<Long, AtomicInteger>() {
            @Override
            public AtomicInteger load(Long key) throws Exception {
                return new AtomicInteger(0);
            }
        });
    }


    // ~~~ interface methods

    @Override
    public void add(String sessionId, byte bizChannel, ChannelHandlerContext ctx, Map<String, Object> extraMap) {
        this.add(sessionId, Byte.toString(bizChannel), ctx, extraMap);
    }

    @Override
    public void add(String sessionId, byte bizChannel, ChannelHandlerContext ctx, String poolKey, Map<String, Object> extraMap) {
        this.add(sessionId, Byte.toString(bizChannel), ctx, poolKey, extraMap);
    }

    @Override
    public void add(String sessionId, String channel, ChannelHandlerContext ctx, Map<String, Object> extraMap) {
        Session session = sessionManager.createAndSaveSession(sessionId, channel, ctx, extraMap);
        Connection connection = new Connection(session, channel);
        saveConnection(channel, connection);
//        this.connectionMap.put(connection.getPoolKey(), connection);
//        if (!zimConfiguration.isNotRegister()) {
//            this.registryService.addConnectionNum();
//        }
        ctx.channel().attr(PUSH_FAILURE_COUNT).set(0);
        publishConnectEvent(sessionId, extraMap);
    }

    @Override
    public void add(String sessionId, String channel, ChannelHandlerContext ctx, String poolKey, Map<String, Object> extraMap) {
        Session session = sessionManager.createAndSaveSession(sessionId, channel, ctx, extraMap);
        Connection connection = new Connection(session, poolKey, channel);
//        if (!zimConfiguration.isNotRegister()) {
//            this.registryService.addConnectionNum();
//        }
        saveConnection(channel, connection);
//        this.connectionMap.put(connection.getPoolKey(), connection);
        publishConnectEvent(sessionId, extraMap);
    }

    @Override
    public void bind(String sessionId, byte bizChannel, ChannelHandlerContext ctx, Map<String, Object> extraMap) {
        this.bind(sessionId, bizChannel, ctx, extraMap);
    }

    private void saveConnection(String channel, Connection connection) {
        Map<String, Connection> channelMap = this.connectionMap.get(connection.getPoolKey());
        if (channelMap == null) {
            channelMap = new ConcurrentHashMap<>();
            this.connectionMap.put(connection.getPoolKey(), channelMap);
        }
        channelMap.put(channel, connection);
    }

    @Override
    public void bind(String sessionId, String bizChannel, ChannelHandlerContext ctx, Map<String, Object> extraMap) {
        Session session = sessionManager.updateSession(sessionId, bizChannel, ctx, extraMap);
        justRemoveOldConnection(sessionId, bizChannel, true);
        Connection connection = new Connection(session, bizChannel);
        ctx.channel().attr(PUSH_FAILURE_COUNT).set(0);
        this.saveConnection(bizChannel, connection);
    }

    public void justRemoveOldConnection(String sessionId, String bizChannel, boolean isBind) {
        Connection oldConnection = getConnection(sessionId, bizChannel);
        if (oldConnection != null) {
            this.removeConnection(sessionId, bizChannel);
            Session oldSession = oldConnection.getSession();
            String transport = oldSession.getCtx().channel().attr(TRANSPORT).get();
            // 长轮询此处可能导致刚连得链接被关闭
            if (!"polling".equals(transport)) {
                oldConnection.close();
                if (!zimConfiguration.isNotRegister() && !isBind) {
                    this.registryService.decreaseConnectionNum();
                }
            }
        }
    }

    /**
     * @see ConnectionManager#get(String)
     */
    @Override
    public List<Connection> get(String poolKey) {
        return this.connectionMap.get(poolKey) != null ? new ArrayList<>(this.connectionMap.get(poolKey).values()) : null;
    }

    @Override
    public Connection get(String poolKey, byte bizChannel) {
        return this.getConnection(poolKey, Byte.toString(bizChannel));
    }

    @Override
    public List<Connection> getOtherChannelConn(String poolKey, byte bizChannel) {
        Map<String, Connection> channelConnMap = this.connectionMap.get(poolKey);
        List<Connection> otherChannel = null;
        if (MapUtils.isNotEmpty(channelConnMap)) {
            otherChannel = channelConnMap.entrySet().stream().filter(entry -> !entry.getKey().equals(Byte.toString(bizChannel))).map(entry -> entry.getValue()).collect(Collectors.toList());
        }
        return otherChannel;
    }

    @Override
    public Connection get(String poolKey, String bizChannel) {
        return this.getConnection(poolKey, bizChannel);
    }

    /**
     * @see ConnectionManager#remove(Connection, ChannelHandlerContext)
     */
    @Override
    public void remove(Connection connection, ChannelHandlerContext ctx) {
        if (null == connection) {
            return;
        }
        this.remove(connection.getPoolKey(), connection.getBizChannel(), ctx);
    }

    @Override
    public void remove(String poolKey, String bizChannel, ChannelHandlerContext currentCtx) {
        if (StringUtils.isBlank(poolKey)) {
            return;
        }
        Connection connection = getConnection(poolKey, bizChannel);
        if (connection != null) {
            Session session = connection.getSession();
            ChannelHandlerContext ctx1 = session.getCtx();
            String transport = ctx1.channel().attr(TRANSPORT).get();
            // currentCtx为当前关闭的ChannelHandlerContext
            if (!currentCtx.channel().id().equals(ctx1.channel().id())) {
                currentCtx.close();
                return;
            }
//            if (!"polling".equals(transport) && !zimConfiguration.isNotRegister()) {
//                this.registryService.decreaseConnectionNum();
//            }
            removeConnection(poolKey, bizChannel);
            // 长轮询此处可能导致刚连得链接被关闭
            if (!"polling".equals(transport)) {
                // 判断当前redis中注册的节点信息是否本节点，如果是本节点，执行remove，否则不操作
                logger.info("ws connection close {}", session);
                close(connection, session);
            }
        }
    }

    @Override
    public void remove(Connection connection) {
        if (null == connection) {
            return;
        }
        String poolKey = connection.getPoolKey();
        String bizChannel = connection.getBizChannel();
        if (StringUtils.isBlank(poolKey)) {
            return;
        }
        if (connection != null) {
//            channelConnMap.remove(channel);
//            this.connectionMap.remove(poolKey);
            this.removeConnection(poolKey, bizChannel);
            Session session = connection.getSession();
            String transport = session.getCtx().channel().attr(TRANSPORT).get();
            // 长轮询此处可能导致刚连得链接被关闭
            if (!"polling".equals(transport)) {
                logger.info("ws connection closed {}", session);
                close(connection, session);
//                if (!zimConfiguration.isNotRegister()) {
//                    this.registryService.decreaseConnectionNum();
//                }
            }
        }
    }

    public void remove(String poolKey) {
        if (StringUtils.isBlank(poolKey)) {
            return;
        }
        Map<String, Connection> channelConnMap = this.connectionMap.get(poolKey);
        if (MapUtils.isNotEmpty(channelConnMap)) {
            channelConnMap.values().stream().forEach(conn -> {
                this.remove(conn);
            });
        }
    }


    private void close(Connection connection, Session session) {
        String nodeAddress = (String) redisTemplate.opsForHash().get(session.getSessionId(), connection.getBizChannel());
        if (NODE_ADDRESS.equals(nodeAddress)) {
            this.sessionManager.removeSession(session, connection.getBizChannel());
        }
        publishCloseEvent(session.getSessionId(), session.getExtraMap());
        connection.close();
    }

    private void removeConnection(String poolKey, String channel) {
        Map<String, Connection> channelConnMap = this.connectionMap.get(poolKey);
        if (MapUtils.isEmpty(channelConnMap)) {
            return;
        }
        if (channelConnMap.size() == 1
                && channelConnMap.get(channel) != null) {
            logger.debug("remove connection info by poolKey, poolKey: {}, channel: {}, connection: {}"
                    , poolKey, channel, JSON.toJSONString(channelConnMap));
            this.connectionMap.remove(poolKey);
        } else {
            logger.debug("remove connection info by channel, poolKey: {}, channel: {}, connection: {}"
                    , poolKey, channel, JSON.toJSONString(channelConnMap));
            channelConnMap.remove(channel);
        }
    }

    private Connection getConnection(String poolKey, String channel) {
        Map<String, Connection> channelConnMap = this.connectionMap.get(poolKey);
        if (MapUtils.isNotEmpty(channelConnMap)) {
            return channelConnMap.get(channel);
        }
        return null;
    }

    /**
     * Warning! This is weakly consistent implementation, to prevent lock the whole {@link ConcurrentHashMap}.
     *
     * @see ConnectionManager#removeAll()
     */
    @Override
    public void removeAll() {
        if (null == this.connectionMap || this.connectionMap.isEmpty()) {
            return;
        }
        if (null != this.connectionMap && !this.connectionMap.isEmpty()) {
            Iterator<String> iter = this.connectionMap.keySet().iterator();
            while (iter.hasNext()) {
                String poolKey = iter.next();
                this.remove(poolKey);
                iter.remove();
            }
            logger.warn("All connection pool and connections have been removed!");
        }
    }

    /**
     * @see ConnectionManager#check(Connection)
     */
    @Override
    public void check(Connection connection)
            throws ConnectionNotWritableException, ConnectionInvalidException {
        if (connection == null) {
            throw new ConnectionInvalidException("Connection is null when do check!");
        }
        if (connection.getChannel() == null || (!connection.getChannel().isActive() && !"polling".equals(connection.getChannel().attr(TRANSPORT).get()))) {
            this.remove(connection);
            throw new ConnectionInvalidException("Check connection failed for address: ");
        }
        if (configMananger.isWritableControlSwitch() && !connection.getChannel().isWritable()) {
            // No remove. Most of the time it is unwritable temporarily.
            throw new ConnectionNotWritableException("Check connection failed for address, maybe write overflow!");
        }
    }

    @Override
    public int incFailureCount(Long msgId) {
        AtomicInteger failureCount = msgSendFailureCounter.getUnchecked(msgId);
        if (failureCount != null) {
            failureCount.incrementAndGet();
        } else {
            failureCount = new AtomicInteger(1);
            msgSendFailureCounter.put(msgId, failureCount);
        }
        return failureCount.get();
    }

    /**
     * in case of cache pollution and connection leak, to do schedule scan
     */
    @Override
    public void scan() {
        if (null != this.connectionMap && !this.connectionMap.isEmpty()) {
            this.connectionMap.forEach((poolKey, connectionMap) -> {
                if (MapUtils.isNotEmpty(connectionMap)) {
                    connectionMap.values().stream().forEach(connection -> {
                        if (!connection.isFine()) {
                            this.remove(connection);
                            logger.warn("Remove non active connection {}", poolKey);
                        }
                    });
                }
            });
        }
    }


    // ~~~ getters and setters

    /**
     * Getter method for property <tt>connectionEventHandler</tt>.
     *
     * @return property value of connectionEventHandler
     */
    public ImConnectionEventHandler getConnectionEventHandler() {
        return connectionEventHandler;
    }

    /**
     * Setter method for property <tt>connectionEventHandler</tt>.
     *
     * @param connectionEventHandler value to be assigned to property connectionEventHandler
     */
    public void setConnectionEventHandler(ImConnectionEventHandler connectionEventHandler) {
        this.connectionEventHandler = connectionEventHandler;
    }

    /**
     * Getter method for property <tt>connectionEventListener</tt>.
     *
     * @return property value of connectionEventListener
     */
    public ConnectionEventListener getConnectionEventListener() {
        return connectionEventListener;
    }

    /**
     * Setter method for property <tt>connectionEventListener</tt>.
     *
     * @param connectionEventListener value to be assigned to property connectionEventListener
     */
    public void setConnectionEventListener(ConnectionEventListener connectionEventListener) {
        this.connectionEventListener = connectionEventListener;
    }

    private void publishConnectEvent(String userId, Map<String, Object> extraMap) {
        ConnectionEstablishEvent event = new ConnectionEstablishEvent();
        event.setUserId(userId);
        event.setEventTime(new Date());
        Byte channel = 0;
        if (MapUtils.isNotEmpty(extraMap)) {
            String conversationId = MapUtils.getString(extraMap, "conversationId");
            channel = MapUtils.getByte(extraMap, "channel");
            event.setConversationId(conversationId);
            event.setChannel(channel);
        }
        RemotingCommand command = connectionEstablishEventConverter.convert2remotingCommand(event);
        eventPublisherService.publish(event);
        messageSendService.asyncSendMessage(userId, command);
        UserEvent userEvent = new UserEvent().setUserIds(Arrays.asList(userId))
                .setEventType(UserEventType.ONLINE).setEventTime(new Date())
                .setIpAddress(NODE_ADDRESS).setChannel(channel);
        messageSendService.asyncSendUserEvent(userId, userEvent);
    }

    private void publishCloseEvent(String userId, Map<String, Object> extraMap) {
        ConnectionCloseEvent event = new ConnectionCloseEvent();
        event.setUserId(userId);
        event.setEventTime(new Date());
        byte channel = 0;
        if (MapUtils.isNotEmpty(extraMap)) {
            String conversationId = MapUtils.getString(extraMap, "conversationId");
            channel = MapUtils.getByte(extraMap, "channel");
            event.setConversationId(conversationId);
            event.setChannel(channel);
        }
        RemotingCommand command = connectionCloseEventConverter.convert2remotingCommand(event);
        eventPublisherService.publish(event);
        messageSendService.asyncSendMessage(userId, command);
        UserEvent userEvent = new UserEvent().setUserIds(Arrays.asList(userId))
                .setEventType(UserEventType.OFFLINE).setEventTime(new Date())
                .setIpAddress(NODE_ADDRESS).setChannel(channel);
        messageSendService.asyncSendUserEvent(userId, userEvent);
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public RegistryService getRegistryService() {
        return registryService;
    }

    public void setRegistryService(RegistryService registryService) {
        this.registryService = registryService;
    }

    public MessageSendService getMessageSendService() {
        return messageSendService;
    }

    public void setMessageSendService(MessageSendService messageSendService) {
        this.messageSendService = messageSendService;
    }

    public EventPublisherService getEventPublisherService() {
        return eventPublisherService;
    }

    public void setEventPublisherService(EventPublisherService eventPublisherService) {
        this.eventPublisherService = eventPublisherService;
    }

    public ConnectionEstablishEventConverter getConnectionEstablishEventConverter() {
        return connectionEstablishEventConverter;
    }

    public void setConnectionEstablishEventConverter(ConnectionEstablishEventConverter connectionEstablishEventConverter) {
        this.connectionEstablishEventConverter = connectionEstablishEventConverter;
    }

    public ConnectionCloseEventConverter getConnectionCloseEventConverter() {
        return connectionCloseEventConverter;
    }

    public void setConnectionCloseEventConverter(ConnectionCloseEventConverter connectionCloseEventConverter) {
        this.connectionCloseEventConverter = connectionCloseEventConverter;
    }

    public ConfigMananger getConfigMananger() {
        return configMananger;
    }

    public void setConfigMananger(ConfigMananger configMananger) {
        this.configMananger = configMananger;
    }

    public ZimConfiguration getZimConfiguration() {
        return zimConfiguration;
    }

    public void setZimConfiguration(ZimConfiguration zimConfiguration) {
        this.zimConfiguration = zimConfiguration;
    }

}
