package com.mrxu.server.utils;


import com.mrxu.server.rpc.Connection;
import com.mrxu.server.rpc.InvokeFuture;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class ConnectionUtil {

    public static Connection getConnectionFromChannel(Channel channel) {
        if (channel == null) {
            return null;
        }

        Attribute<Connection> connAttr = channel.attr(Connection.CONNECTION);
        if (connAttr != null) {
            Connection connection = connAttr.get();
            return connection;
        }
        return null;
    }


    public static void addIdGroupCallbackMapping(Integer id, InvokeFuture callback, Channel channel) {
        Connection connection = getConnectionFromChannel(channel);
        if (connection != null) {
            connection.addInvokeFuture(callback);
        }
    }

    public static InvokeFuture removeIdGroupCallbackMapping(Integer id, Channel channel) {
        Connection connection = getConnectionFromChannel(channel);
        if (connection != null) {
            return connection.removeInvokeFuture(id);
        }
        return null;
    }

    public static InvokeFuture getGroupRequestCallBack(Integer id, Channel channel) {
        Connection connection = getConnectionFromChannel(channel);
        if (connection != null) {
            return connection.getInvokeFuture(id);
        }

        return null;
    }
}
