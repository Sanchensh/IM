/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mrxu.server.processor;

import com.mrxu.common.ImCommandType;
import com.mrxu.common.utils.NamedThreadFactory;
import com.mrxu.mq.ZMSTemplate;
import com.mrxu.proxy.session.SessionManager;
import com.mrxu.remote.ZimConfiguration;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.server.rpc.CommandFactory;
import com.mrxu.server.rpc.RemotingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * Manager of processors<br>
 * Maintains the relationship between command and command processor through command code.
 *
 * @author jiangping
 * @version $Id: BizProcessorManager.java, v 0.1 Sept 6, 2015 2:49:47 PM tao Exp $
 */
@Component
public class BizProcessorManager {
    private static final Logger logger = LoggerFactory.getLogger(BizProcessorManager.class);
    private ConcurrentHashMap<Byte, RemotingProcessor<?>> cmd2processors = new ConcurrentHashMap<Byte, RemotingProcessor<?>>(
            4);

    private RemotingProcessor<?> defaultProcessor;

    /**
     * The default executor, if no executor is set for processor, this one will be used
     */
    private ExecutorService defaultExecutor;
    @Autowired
    private CommandFactory commandFactory;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private ZMSTemplate zmsTemplate;
    @Autowired
    private ZimConfiguration zimConfiguration;

    private int minPoolSize = 20;

    private int maxPoolSize = 400;

    private int queueSize = 600;

    private long keepAliveTime = 60;

    @PostConstruct
    public void init() {
        defaultExecutor = new ThreadPoolExecutor(minPoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize), new NamedThreadFactory("Bolt-default-executor", true));
        this.registerDefaultProcessor(new AbstractRemotingProcessor<RemotingCommand>() {
            @Override
            public void doProcess(RemotingContext ctx, RemotingCommand msg) throws Exception {
                logger.error("No processor available for command code {}, msgId {}", msg.getCmdCode(), msg.getId());
            }
        });
        this.registerProcessor(ImCommandType.REQUEST, new ImRequestProcessor(commandFactory));
        this.registerProcessor(ImCommandType.RESPONSE, new ImResponseProcessor(zmsTemplate, zimConfiguration));
        this.registerProcessor(ImCommandType.HEARTBEAT, new ImHeartBeatProcessor(sessionManager));
    }

    /**
     * Register processor to process command that has the command code of cmdCode.
     *
     * @param protocolCode
     * @param processor
     */
    public void registerProcessor(Byte protocolCode, RemotingProcessor<?> processor) {
        if (this.cmd2processors.containsKey(protocolCode)) {
            logger.warn("Processor for cmd={} is already registered, the processor is {}, and changed to {}",
                    protocolCode, cmd2processors.get(protocolCode).getClass().getName(),
                    processor.getClass().getName());
        }
        this.cmd2processors.put(protocolCode, processor);
    }

    /**
     * Register the default processor to process command with no specific processor registered.
     *
     * @param processor
     */
    public void registerDefaultProcessor(RemotingProcessor<?> processor) {
        if (this.defaultProcessor == null) {
            this.defaultProcessor = processor;
        } else {
            throw new IllegalStateException(
                    "The defaultProcessor has already been registered: " + this.defaultProcessor.getClass());
        }
    }

    /**
     * Get the specific processor with command code of cmdCode if registered, otherwise the default processor is returned.
     *
     * @param protocolCode
     * @return
     */
    public RemotingProcessor<?> getProcessor(Byte protocolCode) {
        RemotingProcessor<?> processor = this.cmd2processors.get(protocolCode);
        if (processor != null) {
            return processor;
        }
        return this.defaultProcessor;
    }

    /**
     * Getter method for property <tt>defaultExecutor</tt>.
     *
     * @return property value of defaultExecutor
     */
    public ExecutorService getDefaultExecutor() {
        return defaultExecutor;
    }

    /**
     * Set the default executor.
     *
     * @param executor
     */
    public void registerDefaultExecutor(ExecutorService executor) {
        this.defaultExecutor = executor;
    }

}
