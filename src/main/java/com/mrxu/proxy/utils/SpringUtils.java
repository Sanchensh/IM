package com.mrxu.proxy.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description: Spring工具类获取对应服务
 * @author: ztowh
 * @Date: 2018/12/5 14:59
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T> T getService(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
