package com.mrxu.common.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

/**
 * Utility of pool configuration.
 *
 * @author kevin
 */
public class PoolConfigUtil {

    public static GenericObjectPoolConfig poolConfig(RedisProperties.Pool pool) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        if (pool.getMaxWait() != null) {
            config.setMaxWaitMillis(pool.getMaxWait().toMillis());
        }
        return config;
    }

}
