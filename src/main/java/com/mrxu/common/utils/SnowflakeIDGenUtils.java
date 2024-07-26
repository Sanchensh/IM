package com.mrxu.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Random;

public class SnowflakeIDGenUtils {


    static private final Logger LOGGER = LoggerFactory.getLogger(SnowflakeIDGenUtils.class);

    private final long twepoch = 1288834974657L;
    private final long workerIdBits = 10L;
    //    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);//最大能够分配的workerid =1023
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    private static long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    //    public boolean initFlag = false;
    private static final Random RANDOM = new Random();


    private static SnowflakeIDGenUtils snowflakeIDGen = new SnowflakeIDGenUtils();

    private SnowflakeIDGenUtils() {
        InetAddress address;
        try {
            address = NetUtils.getLocalAddress();
        } catch (final Exception e) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }
        byte[] ipAddressByteArray = address.getAddress();
        workerId = (((ipAddressByteArray[ipAddressByteArray.length - 2] & 0B11) << Byte.SIZE) + (ipAddressByteArray[ipAddressByteArray.length - 1] & 0xFF));
    }

    public static long getId() {
        return snowflakeIDGen.get();
    }

    public synchronized long get() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    wait(offset << 1);
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        return -1;
                    }
                } catch (InterruptedException e) {
                    LOGGER.error("wait interrupted");
                    return -2;
                }
            } else {
                return -3;
            }
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                //seq 为0的时候表示是下一毫秒时间开始对seq做随机
                sequence = RANDOM.nextInt(100);
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //如果是新的ms开始
            sequence = RANDOM.nextInt(100);
        }
        lastTimestamp = timestamp;
        long id = ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
        return id;

    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public long getWorkerId() {
        return workerId;
    }

}
