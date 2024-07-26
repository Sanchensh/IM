package com.mrxu.server.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018-12-18 15:37
 */
@Component
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String PREFIX = "token_";

    public boolean isValid(String biz, String userId, String token, byte channel) {
        List<String> validTokens = stringRedisTemplate.opsForValue()
                .multiGet(Arrays.asList(PREFIX + biz + ":" + userId, PREFIX + biz + ":" + channel + ":" + userId));
        if (CollectionUtils.isEmpty(validTokens)) {
            return false;
        }
        if (!validTokens.contains(token)) {
            return false;
        }
        return true;
    }

    public boolean touchSession(String userId) {
        Boolean exist = stringRedisTemplate.hasKey(PREFIX + userId);
        if (exist) {
            stringRedisTemplate.expire(PREFIX + userId, 2, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }
}
