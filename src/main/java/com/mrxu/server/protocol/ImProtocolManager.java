package com.mrxu.server.protocol;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: 初始化所有的protocol
 * @author: ztowh
 * @Date: 2018/11/23 15:33
 */
@Component
public class ImProtocolManager implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Protocol> protocolMap = applicationContext.getBeansOfType(Protocol.class);
        for (Map.Entry<String, Protocol> entry : protocolMap.entrySet()) {
            ProtocolManager.registerProtocol(entry.getValue(), entry.getValue().getProtocolCode());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
