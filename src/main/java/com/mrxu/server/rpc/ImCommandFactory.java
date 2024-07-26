package com.mrxu.server.rpc;

import com.mrxu.common.ResponseStatus;
import com.mrxu.common.domain.biz.Command;
import com.mrxu.common.domain.biz.response.ErrorResponseCommand;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.domain.request.ImRequestCommand;
import com.mrxu.remote.domain.response.ImResponseCommand;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/23 13:48
 */
@Component
public class ImCommandFactory implements CommandFactory {

    @Override
    public ImRequestCommand createRequestCommand(byte channel, Object requestObject) {
        return new ImRequestCommand(channel, (Command) requestObject);
    }

    @Override
    public ImResponseCommand createResponse(Object responseObject, RemotingCommand requestCmd) {
        ImResponseCommand response = new ImResponseCommand(requestCmd.getId(), (Command) responseObject);
        response.setSerializer(requestCmd.getSerializer());
        response.setResponseStatus(ResponseStatus.SUCCESS);
        return response;
    }

    @Override
    public ImResponseCommand createExceptionResponse(long id, String errMsg) {
        return createExceptionResponse(id, null, errMsg);
    }

    @Override
    public ImResponseCommand createExceptionResponse(long id, Throwable t, String errMsg) {
        ImResponseCommand response = null;
        if (null == t) {
            response = new ImResponseCommand(id, createServerException(errMsg));
        } else {
            response = new ImResponseCommand(id, createServerException(t, errMsg));
        }
        response.setResponseStatus(ResponseStatus.SERVER_EXCEPTION);
        return response;
    }

    @Override
    public ImResponseCommand createExceptionResponse(long id, ResponseStatus status) {
        ImResponseCommand responseCommand = new ImResponseCommand();
        responseCommand.setId(id);
        responseCommand.setResponseStatus(status);
        return responseCommand;
    }

    @Override
    public ImResponseCommand createExceptionResponse(long id, ResponseStatus status, Throwable t) {
        ImResponseCommand responseCommand = this.createExceptionResponse(id, status);
        responseCommand.setResponseObject(createServerException(t, null));
        return responseCommand;
    }

    /**
     * create server exception using error msg, no stack trace
     *
     * @param errMsg the assigned error message
     * @return an instance of RpcServerException
     */
    private ErrorResponseCommand createServerException(String errMsg) {
        return new ErrorResponseCommand(errMsg);
    }

    /**
     * create server exception using error msg and fill the stack trace using the stack trace of throwable.
     *
     * @param t      the origin throwable to fill the stack trace of rpc server exception
     * @param errMsg additional error msg, <code>null</code> is allowed
     * @return an instance of RpcServerException
     */
    private ErrorResponseCommand createServerException(Throwable t, String errMsg) {
        String formattedErrMsg = String.format(
                "[Server]OriginErrorMsg: %s: %s. AdditionalErrorMsg: %s", t.getClass().getName(),
                ExceptionUtils.getRootCauseMessage(t), errMsg);
        ErrorResponseCommand e = new ErrorResponseCommand(formattedErrMsg);
        return e;
    }
}
