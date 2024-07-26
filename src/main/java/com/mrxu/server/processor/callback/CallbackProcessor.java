package com.mrxu.server.processor.callback;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.Command;
import com.mrxu.common.exception.ConnectionInvalidException;
import com.mrxu.common.exception.ConnectionNotWritableException;
import com.mrxu.remote.domain.RemotingCommand;

public interface CallbackProcessor<T extends Command> {

    ImCommandCode getCmdCode();

    void callback(T cmd, RemotingCommand remotingCommand)
            throws ConnectionInvalidException, ConnectionNotWritableException;

    void dispatch2Sender(T cmd, RemotingCommand remotingCommand) throws ConnectionInvalidException, ConnectionNotWritableException;
}
