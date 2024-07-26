package com.mrxu.server.metrics;

import com.mrxu.mq.ZMSTemplate;
import com.mrxu.server.metrics.registry.KafkaMeterRegistry;
import com.mrxu.server.metrics.registry.KafkaRegistryConfig;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mrxu.proxy.utils.NetUtils.NODE_ADDRESS;

/**
 * @author: zhaoyi.wang
 * @date: 2020/1/2 3:31 下午
 * @description:
 */
@Configuration
public class MetricsConfig {

    @Autowired
    private ZMSTemplate zmsTemplate;

    @Bean
    public KafkaRegistryConfig customRegistryConfig() {
        return KafkaRegistryConfig.DEFAULT;
    }

    @Bean
    public KafkaMeterRegistry customMeterRegistry(KafkaRegistryConfig kafkaRegistryConfig, Clock clock) {
        KafkaMeterRegistry kafkaMeterRegistry = new KafkaMeterRegistry(kafkaRegistryConfig, clock, zmsTemplate);
        kafkaMeterRegistry.config().commonTags("ip", NODE_ADDRESS);
        return kafkaMeterRegistry;
    }

    @Bean("requestTimer")
    public Timer requestMeter(KafkaMeterRegistry kafkaMeterRegistry) {
        return kafkaMeterRegistry.timer("tps");
    }

}
