package com.mrxu.server.metrics;


import com.mrxu.server.DirectMemoryReporter;
import com.mrxu.server.ZimServer;
import com.mrxu.server.metrics.registry.KafkaMeterRegistry;
import com.mrxu.server.registry.RegistryService;
import io.micrometer.core.instrument.Gauge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: zhaoyi.wang
 * @date: 2019-04-29 16:48
 * @description: im 系统信息统计
 */
@Slf4j
@Component
public class ZimMicroMeterMetrics {

    @Autowired
    private RegistryService registryService;

    @Autowired
    private ZimServer zimServer;

    @Autowired
    private KafkaMeterRegistry kafkaMeterRegistry;

    @PostConstruct
    public void init() {

        Gauge.builder("im_connection_nums", () -> registryService.getConnectionNum()).register(kafkaMeterRegistry);

        Gauge.builder("direct_memory", () -> DirectMemoryReporter.getInstance().getDirectMemory()).register(kafkaMeterRegistry);

        Gauge.builder("traffic_read", () -> {
            if (zimServer.getGlobalTrafficShapingHandler() == null) {
                return 0L;
            }
            return zimServer.getGlobalTrafficShapingHandler().trafficCounter().lastReadBytes();
        }).register(kafkaMeterRegistry);

        Gauge.builder("traffic_write", () -> {
            if (zimServer.getGlobalTrafficShapingHandler() == null) {
                return 0L;
            }
            return zimServer.getGlobalTrafficShapingHandler().trafficCounter().lastWrittenBytes();
        }).register(kafkaMeterRegistry);

        Gauge.builder("traffic_read_throughput", () -> {
            if (zimServer.getGlobalTrafficShapingHandler() == null) {
                return 0L;
            }
            return zimServer.getGlobalTrafficShapingHandler().trafficCounter().lastReadThroughput();
        }).register(kafkaMeterRegistry);

        Gauge.builder("traffic_write_throughput", () -> {
            if (zimServer.getGlobalTrafficShapingHandler() == null) {
                return 0L;
            }
            return zimServer.getGlobalTrafficShapingHandler().trafficCounter().lastWriteThroughput();
        }).register(kafkaMeterRegistry);

    }

}
