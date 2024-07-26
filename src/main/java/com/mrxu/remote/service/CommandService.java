package com.mrxu.remote.service;


import com.mrxu.common.exception.ConnectionInvalidException;
import com.mrxu.common.exception.ConnectionNotWritableException;
import com.mrxu.remote.domain.ImCommand;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/12/5 13:46
 */
public interface CommandService {

    void processCommand(ImCommand command) throws ConnectionNotWritableException, ConnectionInvalidException;


    void dispatch2Sender(ImCommand command) throws ConnectionNotWritableException, ConnectionInvalidException;
}
