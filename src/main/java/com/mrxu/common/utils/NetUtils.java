package com.mrxu.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetUtils {
    private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");
    public static final String ANYHOST = "0.0.0.0";
    public static final String LOCALHOST = "127.0.0.1";
    private static volatile InetAddress LOCAL_ADDRESS = null;

    public NetUtils() {
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address != null && !address.isLoopbackAddress()) {
            String name = address.getHostAddress();
            return name != null && !"0.0.0.0".equals(name) && !"127.0.0.1".equals(name) && IP_PATTERN.matcher(name).matches();
        } else {
            return false;
        }
    }

    public static boolean isLocalHost(String host) {
        return host != null && (LOCAL_IP_PATTERN.matcher(host).matches() || "localhost".equalsIgnoreCase(host));
    }

    public static boolean isAnyHost(String host) {
        return "0.0.0.0".equals(host);
    }

    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        } else {
            InetAddress localAddress = getLocalAddress0();
            LOCAL_ADDRESS = localAddress;
            return localAddress;
        }
    }

    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;

        Throwable e;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable var6) {
            e = var6;
            logger.warn("Failed to retriving ip address, " + e.getMessage(), e);
        }

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while(interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = (NetworkInterface)interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while(addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = (InetAddress)addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e1) {
                                    logger.warn("Failed to retriving ip address, " + e1.getMessage(), e1);
                                }
                            }
                        }
                    } catch (Throwable e2) {
                        logger.warn("Failed to retriving ip address, " + e2.getMessage(), e2);
                    }
                }
            }
        } catch (Throwable var8) {
            e = var8;
            logger.warn("Failed to retriving ip address, " + e.getMessage(), e);
        }

        logger.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }
}
