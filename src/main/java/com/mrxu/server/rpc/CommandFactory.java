package com.mrxu.server.rpc;

import com.mrxu.common.ResponseStatus;
import com.mrxu.remote.domain.RemotingCommand;

public interface CommandFactory {
    // ~~~ create request command

    /**
     * create a request command with request object
     *
     * @param requestObject the request object included in request command
     * @param <T>
     * @return
     */
    <T extends RemotingCommand> T createRequestCommand(byte channel, final Object requestObject);

    // ~~~ create response command

    /**
     * create a normal response with response object
     *
     * @param responseObject
     * @param requestCmd
     * @param <T>
     * @return
     */
    <T extends RemotingCommand> T createResponse(final Object responseObject,
                                                 RemotingCommand requestCmd);

    <T extends RemotingCommand> T createExceptionResponse(long id, String errMsg);

    <T extends RemotingCommand> T createExceptionResponse(long id, final Throwable t, String errMsg);

    <T extends RemotingCommand> T createExceptionResponse(long id, ResponseStatus status);

    <T extends RemotingCommand> T createExceptionResponse(long id, ResponseStatus status,
                                                          final Throwable t);

}
