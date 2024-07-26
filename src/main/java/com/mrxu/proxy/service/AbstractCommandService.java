package com.mrxu.proxy.service;

import com.alibaba.fastjson.JSON;
import com.mrxu.remote.domain.ImCommand;
import com.mrxu.remote.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 发送command的dubbo服务
 * @author: ztowh
 * @Date: 2018/12/5 14:43
 */
public abstract class AbstractCommandService implements CommandService {

    private Logger logger = LoggerFactory.getLogger(AbstractCommandService.class);

    @Override
    public void processCommand(ImCommand command) {
        long start = System.currentTimeMillis();
        process(command);
        logger.info("process command cost {}", JSON.toJSON(command));
    }

    public abstract void process(ImCommand command);
}
