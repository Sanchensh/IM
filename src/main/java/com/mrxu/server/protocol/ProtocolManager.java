package com.mrxu.server.protocol;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ProtocolManager {

    private static final ConcurrentMap<Byte, Protocol> protocols = new ConcurrentHashMap<Byte, Protocol>();

    public static Protocol getProtocol(byte protocolCode) {
        return protocols.get(protocolCode);
    }

    public static void registerProtocol(Protocol protocol, byte protocolCode) {
        if (null == protocol) {
            throw new RuntimeException("Protocol: " + protocol + " and protocol code:"
                    + protocolCode + " should not be null!");
        }
        Protocol exists = ProtocolManager.protocols.putIfAbsent(protocolCode, protocol);
        if (exists != null) {
            throw new RuntimeException("Protocol for code: " + protocolCode + " already exists!");
        }
    }

    public static Protocol unRegisterProtocol(byte protocolCode) {
        return ProtocolManager.protocols.remove(protocolCode);
    }
}