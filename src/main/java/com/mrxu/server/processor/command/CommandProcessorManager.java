package com.mrxu.server.processor.command;

import com.mrxu.common.ImCommandCode;
import com.mrxu.server.processor.CommandProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description: 注册
 * @author: ztowh
 * @Date: 2018/11/30 16:18
 */
@Component
public class CommandProcessorManager implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private static final ConcurrentMap<ImCommandCode, CommandProcessor<?>> processors = new ConcurrentHashMap<ImCommandCode, CommandProcessor<?>>();

    public static void registerUserProcessor(ImCommandCode commandCode, CommandProcessor commandProcessor) {
        if (null == commandCode || null == commandProcessor) {
            throw new RuntimeException("UserProcessor: " + commandProcessor + " and command code:"
                    + commandCode + " should not be null!");
        }
        CommandProcessor exists = CommandProcessorManager.processors.putIfAbsent(commandCode, commandProcessor);
        if (exists != null) {
            throw new RuntimeException("UserProcessor for code: " + commandProcessor + " already exists!");
        }
    }

    public static CommandProcessor getCommandProcessor(ImCommandCode commandCode) {
        return processors.get(commandCode);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, CommandProcessor> commandProcessorMap = this.applicationContext.getBeansOfType(CommandProcessor.class);
        for (Map.Entry<String, CommandProcessor> e : commandProcessorMap.entrySet()) {
            CommandProcessor processor = e.getValue();
            ImCommandCode cmdCode = processor.getCmdCode();
            registerUserProcessor(cmdCode, processor);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
