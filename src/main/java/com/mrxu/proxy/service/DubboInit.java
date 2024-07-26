package com.mrxu.proxy.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.mrxu.proxy.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.mrxu.proxy.utils.NetUtils.NODE_ADDRESS;


/**
 * @Description: 编程方式初始化dubbo，根据主机ip进行服务分组
 * @author: ztowh
 * @Date: 2018/12/5 17:27
 */
@Component
@ConfigurationProperties(prefix = "dubbo")
@DependsOn("springUtils")
public class DubboInit {

    private Logger logger = LoggerFactory.getLogger(DubboInit.class);

    private static final String DUBBO_PROPERTIES_PATH = "dubbo.properties";

    private String application;

    private String registryAddress;

    private String group;

    @PostConstruct
    public void initService() {

        List<DubboServiceConfig> dubboServiceConfigList = parseDubboConfig();

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(application);


        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress(registryAddress);
        registryConfig.setRegister(true);
        registryConfig.setSubscribe(true);

        String parseGroup = "localhost".equals(group) ? NODE_ADDRESS : group;

        for (DubboServiceConfig serviceConfig : dubboServiceConfigList) {
            try {
                Class<?> interfaceClass = Class.forName(serviceConfig.getInterfaceClass());
                Object impl = SpringUtils.getService(interfaceClass);
                ServiceConfig service = new ServiceConfig(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
                service.setApplication(applicationConfig);
                service.setRegistry(registryConfig); // 多个注册中心可以用setRegistries()
                service.setProtocol(protocolConfig); // 多个协议可以用setProtocols()
                service.setInterface(interfaceClass);
                service.setRef(impl);

                service.setGroup(parseGroup);
                service.setVersion("1.0.0");

                // 暴露及注册服务
                service.export();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private List<DubboServiceConfig> parseDubboConfig() {
        ClassLoader standardClassloader = Thread.currentThread().getContextClassLoader();
        URL url = null;
        if (standardClassloader != null) {
            url = standardClassloader.getResource(DUBBO_PROPERTIES_PATH);
        }
        if (url == null) {
            url = DubboInit.class.getResource(DUBBO_PROPERTIES_PATH);
        }
        if (url != null) {
            logger.debug("Configuring command from command.properties found in the classpath: " + url);
        } else {
            logger.warn("No configuration found. Configuring command from command.properties "
                    + " found in the classpath: {}", url);

        }
        List<DubboServiceConfig> serviceConfigList = new ArrayList<>();
        try (InputStream inputStream = url.openStream()) {
            Properties properties = new Properties();
            properties.load(inputStream);
            for (Object key : properties.keySet()) {
                DubboServiceConfig serviceConfig = new DubboServiceConfig();
                serviceConfig.setInterfaceClass((String) key);
                serviceConfigList.add(serviceConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serviceConfigList;
    }

    class DubboServiceConfig {
        private String interfaceClass;

        public String getInterfaceClass() {
            return interfaceClass;
        }

        public void setInterfaceClass(String interfaceClass) {
            this.interfaceClass = interfaceClass;
        }
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
