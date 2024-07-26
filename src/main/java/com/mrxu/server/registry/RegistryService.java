package com.mrxu.server.registry;

/**
 * @Description: 注册本机的信息（地址、端口、连接数）
 * @author: ztowh
 * @Date: 2018-12-18 13:22
 */
public interface RegistryService {

    void init();

    void destroy();

    void addConnectionNum();

    void decreaseConnectionNum();

    Integer getConnectionNum();
}
