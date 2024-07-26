package com.mrxu.server.config;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.mrxu.server.registry.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.mrxu.proxy.utils.NetUtils.NODE_ADDRESS;

/**
 * @Description: 连接数管理
 * @author: ztowh
 * @Date: 2019-01-06 15:33
 */
@Slf4j
@Component
public class ConnectionLimiter {

    private Map<String, Integer> connLimitMap = new HashMap<>();

    private static final String CONN_LIMIT_KEY = "connLimit";
    @ApolloConfig
    private Config config;

    @Value("${connLimit}")
    private String connLimitJson;

    @Autowired
    private RegistryService registryService;

    @PostConstruct
    public void init() {
        parseLimitJson(connLimitJson);
    }

    @ApolloConfigChangeListener
    private void connLimitChange(ConfigChangeEvent event) {
        if (event.isChanged(CONN_LIMIT_KEY)) {
            String value = config.getProperty(CONN_LIMIT_KEY, "");
            parseLimitJson(value);
        }
    }

    public boolean canAccept() {
        Integer connectionNum = registryService.getConnectionNum();
        Integer connLimit = connLimitMap.get(NODE_ADDRESS);
        if (connLimit == null) {
            return true;
        }
        if (connectionNum <= connLimit) {
            return true;
        }
        log.warn("no available connections, conns: [{}]", connectionNum);
        return false;
    }

    private void parseLimitJson(String connLimitJson) {
        Map newConnLimitMap = JSON.parseObject(connLimitJson, Map.class);
        connLimitMap = newConnLimitMap;
    }


    public String getConnLimitJson() {
        return connLimitJson;
    }

    public void setConnLimitJson(String connLimitJson) {
        this.connLimitJson = connLimitJson;
    }
}
