package com.mrxu.common;

public class ImCommandType {
    /**
     * rpc response
     */
    public static final byte RESPONSE = (byte) 0x02;
    /**
     * rpc request
     */
    public static final byte REQUEST = (byte) 0x01;
    /**
     * heart beat
     */
    public static final byte HEARTBEAT = (byte) 0x00;
    /**
     * heart beat ack
     */
    public static final byte HEARTBEATACK = (byte)0x03;

}
