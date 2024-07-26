package com.mrxu.server.service;

import com.mrxu.common.exception.ConnectionInvalidException;
import com.mrxu.common.exception.ConnectionNotWritableException;
import com.mrxu.remote.domain.ImCommand;
import com.mrxu.remote.domain.request.ImRequestCommand;
import com.mrxu.remote.service.CommandService;
import com.mrxu.server.processor.callback.CallbackProcessor;
import com.mrxu.server.processor.callback.CallbackProcessorManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-11 17:30
 * @description:
 */
@Slf4j
@Component
public class CallbackCommandService implements CommandService {

    @Autowired
    private CallbackProcessorManager callbackProcessorManager;

    @Override
    public void processCommand(ImCommand command) throws ConnectionNotWritableException, ConnectionInvalidException {
        CallbackProcessor processor = callbackProcessorManager.getCallbackProcessor(command.getCmdCode());
        processor.callback(((ImRequestCommand) command).getRequestObject(), command);
    }

    @Override
    public void dispatch2Sender(ImCommand command) throws ConnectionNotWritableException, ConnectionInvalidException {
        CallbackProcessor processor = callbackProcessorManager.getCallbackProcessor(command.getCmdCode());
        processor.dispatch2Sender(((ImRequestCommand) command).getRequestObject(), command);
    }
}
