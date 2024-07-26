package com.mrxu.server.session;

import com.mrxu.proxy.attributes.Attributes;
import com.mrxu.proxy.session.Session;
import com.mrxu.proxy.session.SessionManager;
import com.mrxu.server.registry.RegistryService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.mrxu.common.Constant.SESSION_TIMEOUT;
import static com.mrxu.proxy.utils.NetUtils.NODE_ADDRESS;

/**
 * @Description: 存储session和channel的关系
 * @author: ztowh
 * @Date: 2018/12/4 10:09
 */
@Component
public class ImSessionManager implements SessionManager {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private HashOperations<String, String, String> hashOperations;
    @Autowired
    private RegistryService registryService;

    private static final String USER_ONLINE_LIST = "user_online_list_";

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Session createAndSaveSession(String sessionId, String bizChannel, ChannelHandlerContext ctx, Map<String, Object> extraMap) {
        Session session = new Session(sessionId, ctx, extraMap);
        if (Objects.isNull(session.getExtraMap())) {
            session.setExtraMap(new HashMap());
        }
        session.getExtraMap().put("timestamp", System.currentTimeMillis());
        String userId = session.getSessionId();
        Channel channel = session.getCtx().channel();
//        stringRedisTemplate.opsForValue().set(userId, NODE_ADDRESS);
        hashOperations.put(sessionId, bizChannel, NODE_ADDRESS);
        redisTemplate.expire(sessionId, SESSION_TIMEOUT, TimeUnit.SECONDS);
//        redisTemplate.opsForSet().add("user_online_list", sessionId);
        onlineForUserOnlineList(bizChannel, sessionId);
        this.registryService.addConnectionNum();
        channel.attr(Attributes.SESSION).set(session);
        return session;
    }

    @Override
    public Session updateSession(String sessionId, String bizChannel, ChannelHandlerContext ctx, Map<String, Object> extraMap) {
        Session session = new Session(sessionId, ctx, extraMap);
        if (Objects.isNull(session.getExtraMap())) {
            session.setExtraMap(new HashMap());
        }
        session.getExtraMap().put("timestamp", System.currentTimeMillis());
        ctx.channel().attr(Attributes.SESSION).set(session);
        hashOperations.put(sessionId, bizChannel, NODE_ADDRESS);
        redisTemplate.expire(sessionId, SESSION_TIMEOUT, TimeUnit.SECONDS);
//        redisTemplate.opsForSet().add("user_online_list", sessionId);
        onlineForUserOnlineList(bizChannel, sessionId);
//        stringRedisTemplate.opsForValue().set(sessionId, NODE_ADDRESS, SESSION_TIMEOUT, TimeUnit.SECONDS);
        return session;
    }

    @Override
    public void removeSession(Session session, String bizChannel) {
        String sessionId = session.getSessionId();
        Channel channel = session.getCtx().channel();
//        stringRedisTemplate.delete(sessionId);
        hashOperations.delete(sessionId, bizChannel);
//        redisTemplate.opsForSet().remove("user_online_list", sessionId);
        offlineForUserOnlineList(bizChannel, sessionId);
        this.registryService.decreaseConnectionNum();
        channel.attr(Attributes.SESSION).set(null);
        channel.close();
    }

    @Override
    public void removeSession(String userId, String bizChannel) {
//        Channel channel = session.getCtx().channel();
//        stringRedisTemplate.delete(sessionId);
        hashOperations.delete(userId, bizChannel);
//        redisTemplate.opsForSet().remove("user_online_list", userId);
        offlineForUserOnlineList(bizChannel, userId);
//        channel.attr(Attributes.SESSION).set(null);
//        channel.close();
    }

    @Override
    public void touchSession(String sessionId) {
        redisTemplate.expire(sessionId, SESSION_TIMEOUT, TimeUnit.SECONDS);
    }

    private void onlineForUserOnlineList(String biz, String userId) {
        redisTemplate.opsForSet().add(getUserOnlineListKey(biz, userId), userId);
    }

    private void offlineForUserOnlineList(String biz, String userId) {
        redisTemplate.opsForSet().remove(getUserOnlineListKey(biz, userId), userId);
    }

    private String getUserOnlineListKey(String biz, String userId) {
        int shard = userId.hashCode() % 100;
//        if (StringUtils.isNotBlank(biz)) {
//            return biz + "_" + USER_ONLINE_LIST + shard;
//        }
        return USER_ONLINE_LIST + shard;
    }

}
