package com.mrxu.server.metrics.registry;

import io.micrometer.core.instrument.step.StepRegistryConfig;

/**
 * @author: zhaoyi.wang
 * @date: 2020/1/2 3:24 下午
 * @description:
 */
public interface KafkaRegistryConfig extends StepRegistryConfig {

    /**
     * Accept configuration defaults
     */
    KafkaRegistryConfig DEFAULT = k -> null;

    @Override
    default String prefix() {
        return "kafka";
    }

}
