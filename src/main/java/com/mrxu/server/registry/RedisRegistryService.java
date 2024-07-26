package com.mrxu.server.registry;

import com.mrxu.remote.ZimConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mrxu.common.Constant.colon;
import static com.mrxu.proxy.utils.NetUtils.NODE_ADDRESS;


/**
 * @Description: 通过redis注册机器信息
 * @author: ztowh
 * @Date: 2018-12-18 14:52
 */
@Slf4j
@Component
public class RedisRegistryService implements RegistryService {

    private Logger logger = LoggerFactory.getLogger(RedisRegistryService.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ZimConfiguration zimConfiguration;

    private AtomicInteger currentConn = new AtomicInteger(0);

    @Override
    public void init() {
        String nodeInfo = NODE_ADDRESS + colon + zimConfiguration.getPort();
        if (!zimConfiguration.isNotRegister()) {
            ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
            zSetOperations.remove(zimConfiguration.getRegistry(), nodeInfo);
            zSetOperations.incrementScore(zimConfiguration.getRegistry(), nodeInfo, 0);
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
            valueOperations.set(nodeInfo, zimConfiguration.getRegistry());
        }
    }

    @Override
    public void addConnectionNum() {
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        zSetOperations.incrementScore(zimConfiguration.getRegistry(), NODE_ADDRESS + colon + zimConfiguration.getPort(), 1);
        currentConn.incrementAndGet();
    }

    @Override
    public void decreaseConnectionNum() {
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        if (currentConn.get() > 0) {
            currentConn.decrementAndGet();
            zSetOperations.incrementScore(zimConfiguration.getRegistry(), NODE_ADDRESS + colon + zimConfiguration.getPort(), -1);
        }
    }

    @Override
    public Integer getConnectionNum() {
        return currentConn.get();
    }

    @Override
    @PreDestroy
    public void destroy() {
        String nodeInfo = NODE_ADDRESS + colon + zimConfiguration.getPort();
        stringRedisTemplate.opsForZSet().remove(zimConfiguration.getRegistry(), nodeInfo);
        log.info("节点关闭成功, nodeInfo: {}", nodeInfo);
    }

}
