package com.mrxu.server.rpc;

import com.mrxu.common.exception.ConnectionInvalidException;
import com.mrxu.common.exception.ConnectionNotWritableException;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.Map;

public interface ConnectionManager extends Scannable {


    /**
     * @Description: 根据sessionId，创建连接和session，默认poolkey为sessionId
     * @Params: @param sessionId
     * @Date: 2019-01-02 15:35
     */
    void add(String sessionId, byte bizChannel, ChannelHandlerContext ctx, Map<String, Object> extraMap);

    /**
     * @Description: 根据sessionId，创建连接和session，自定义poolkey
     * @Params:
     * @return:
     * @Date: 2019-01-06 10:55
     */
    void add(String sessionId, byte bizChannel, ChannelHandlerContext ctx, String poolKey, Map<String, Object> extraMap);


    /**
     * @Description: 根据sessionId，创建连接和session，默认poolkey为sessionId
     * @Params: @param sessionId
     * @Date: 2019-01-02 15:35
     */
    void add(String sessionId, String bizChannel, ChannelHandlerContext ctx, Map<String, Object> extraMap);

    /**
     * @Description: 根据sessionId，创建连接和session，自定义poolkey
     * @Params:
     * @return:
     * @Date: 2019-01-06 10:55
     */
    void add(String sessionId, String bizChannel, ChannelHandlerContext ctx, String poolKey, Map<String, Object> extraMap);

    /**
     * @Description: 长轮询时需要重新绑定channel
     * @Params: sessionId, ctx
     * @return:
     * @Date: 2019-03-23 15:47
     */
    void bind(String sessionId, byte bizChannel, ChannelHandlerContext ctx, Map<String, Object> extraMap);

    /**
     * @Description: 长轮询时需要重新绑定channel
     * @Params: sessionId, ctx
     * @return:
     * @Date: 2019-03-23 15:47
     */
    void bind(String sessionId, String bizChannel, ChannelHandlerContext ctx, Map<String, Object> extraMap);


    List<Connection> get(String poolKey);

    Connection get(String poolKey, byte bizChannel);

    List<Connection> getOtherChannelConn(String poolKey, byte bizChannel);

    Connection get(String poolKey, String bizChannel);

    void remove(Connection connection, ChannelHandlerContext ctx);

    void remove(String poolKey, String bizChannel, ChannelHandlerContext ctx);

    void removeAll();

    /**
     * check a connection whether available, if not, throw RemotingException
     *
     * @param connection target connection
     */
    void check(Connection connection)
            throws ConnectionNotWritableException, ConnectionInvalidException;

    void remove(Connection connection);

    int incFailureCount(Long msgId);
}
