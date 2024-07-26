package com.mrxu.server.metrics.registry;

import com.mrxu.mq.ZMSTemplate;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.step.StepMeterRegistry;
import io.micrometer.core.instrument.step.StepRegistryConfig;
import io.micrometer.core.instrument.util.MeterPartition;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringEscapeUtils.escapeJson;

/**
 * @author: zhaoyi.wang
 * @date: 2020/1/2 3:28 下午
 * @description:
 */
@Slf4j
public class KafkaMeterRegistry extends StepMeterRegistry {

    static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    private ZMSTemplate zmsTemplate;

    public KafkaMeterRegistry(StepRegistryConfig config, Clock clock) {
        super(config, clock);
        start(new NamedThreadFactory("kafka-metrics-publisher"));
    }

    public KafkaMeterRegistry(StepRegistryConfig config, Clock clock, ZMSTemplate zmsTemplate) {
        super(config, clock);
        this.zmsTemplate = zmsTemplate;
        start(new NamedThreadFactory("kafka-metrics-publisher"));
    }

    @Override
    protected void publish() {
        for (List<Meter> batch : MeterPartition.partition(this, 10000)) {
            try {
                String metrics = batch.stream()
                        .map(m -> m.match(
                                this::writeGauge,
                                this::writeCounter,
                                this::writeTimer,
                                this::writeSummary,
                                this::writeLongTaskTimer,
                                this::writeTimeGauge,
                                this::writeFunctionCounter,
                                this::writeFunctionTimer,
                                this::writeMeter))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(joining("\n", "", "\n"));
                zmsTemplate.send("zim_metrics_topic", metrics);
                log.debug("Publishing body: " + metrics);
            } catch (Throwable e) {
                log.error("failed to send metrics to kafka", e);
            }
        }
    }

    Optional<String> writeCounter(Counter counter) {
        return writeCounter(counter, counter.count());
    }

    Optional<String> writeFunctionCounter(FunctionCounter counter) {
        return writeCounter(counter, counter.count());
    }

    private Optional<String> writeCounter(Meter meter, Double value) {
        if (Double.isFinite(value)) {
            return Optional.of(writeDocument(meter, builder -> {
                builder.append(",\"count\":").append(value);
            }));
        }
        return Optional.empty();
    }

    /**
     * @return the registry's base TimeUnit. Must not be null.
     */
    @Override
    protected TimeUnit getBaseTimeUnit() {
        return TimeUnit.MILLISECONDS;
    }

    Optional<String> writeGauge(Gauge gauge) {
        Double value = gauge.value();
        if (Double.isFinite(value)) {
            return Optional.of(writeDocument(gauge, builder -> {
                builder.append(",\"value\":").append(value);
            }));
        }
        return Optional.empty();
    }

    Optional<String> writeTimeGauge(TimeGauge gauge) {
        Double value = gauge.value(getBaseTimeUnit());
        if (Double.isFinite(value)) {
            return Optional.of(writeDocument(gauge, builder -> {
                builder.append(",\"value\":").append(value);
            }));
        }
        return Optional.empty();
    }

    Optional<String> writeFunctionTimer(FunctionTimer timer) {
        return Optional.of(writeDocument(timer, builder -> {
            builder.append(",\"count\":").append(timer.count());
            builder.append(",\"sum\" :").append(timer.totalTime(getBaseTimeUnit()));
            builder.append(",\"mean\":").append(timer.mean(getBaseTimeUnit()));
        }));
    }

    Optional<String> writeLongTaskTimer(LongTaskTimer timer) {
        return Optional.of(writeDocument(timer, builder -> {
            builder.append(",\"activeTasks\":").append(timer.activeTasks());
            builder.append(",\"duration\":").append(timer.duration(getBaseTimeUnit()));
        }));
    }

    Optional<String> writeTimer(Timer timer) {
        return Optional.of(writeDocument(timer, builder -> {
            builder.append(",\"count\":").append(timer.count());
            builder.append(",\"sum\":").append(timer.totalTime(getBaseTimeUnit()));
            builder.append(",\"mean\":").append(timer.mean(getBaseTimeUnit()));
            builder.append(",\"max\":").append(timer.max(getBaseTimeUnit()));
        }));
    }

    Optional<String> writeSummary(DistributionSummary summary) {
        summary.takeSnapshot();
        return Optional.of(writeDocument(summary, builder -> {
            builder.append(",\"count\":").append(summary.count());
            builder.append(",\"sum\":").append(summary.totalAmount());
            builder.append(",\"mean\":").append(summary.mean());
            builder.append(",\"max\":").append(summary.max());
        }));
    }

    Optional<String> writeMeter(Meter meter) {
        Iterable<Measurement> measurements = meter.measure();
        List<String> names = new ArrayList<>();
        // Snapshot values should be used throughout this method as there are chances for values to be changed in-between.
        List<Double> values = new ArrayList<>();
        for (Measurement measurement : measurements) {
            double value = measurement.getValue();
            if (!Double.isFinite(value)) {
                continue;
            }
            names.add(measurement.getStatistic().getTagValueRepresentation());
            values.add(value);
        }
        if (names.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(writeDocument(meter, builder -> {
            for (int i = 0; i < names.size(); i++) {
                builder.append(",\"").append(names.get(i)).append("\":\"").append(values.get(i)).append("\"");
            }
        }));
    }

    /**
     * Return formatted current timestamp.
     *
     * @return formatted current timestamp
     * @since 1.2.0
     */
    protected String generateTimestamp() {
        return TIMESTAMP_FORMATTER.format(Instant.ofEpochMilli(config().clock().wallTime()));
    }

    String writeDocument(Meter meter, Consumer<StringBuilder> consumer) {
        StringBuilder sb = new StringBuilder("{ \"index\" : {} }\n");
        String timestamp = generateTimestamp();
        String name = getConventionName(meter.getId());
        String type = meter.getId().getType().toString().toLowerCase();
        sb.append("{\"").append("@timestamp").append("\":\"").append(timestamp).append('"')
                .append(",\"datetime\":\"").append(timestamp).append('"')
                .append(",\"name\":\"").append(escapeJson(name)).append('"')
                .append(",\"type\":\"").append(type).append('"');

        List<Tag> tags = getConventionTags(meter.getId());
        for (Tag tag : tags) {
            sb.append(",\"").append(escapeJson(tag.getKey())).append("\":\"")
                    .append(escapeJson(tag.getValue())).append('"');
        }

        consumer.accept(sb);
        sb.append("}");

        return sb.toString();
    }

}
