package com.mrxu.mq;

import com.mrxu.common.domain.biz.UserEvent;
import com.mrxu.remote.domain.RemotingCommand;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class ZMSTemplate implements DisposableBean {


    public void destroy() throws Exception {
        this.stop();
    }

    public void stop() {


    }

    public void send(String baoheResponseTopic, String s, String jsonString) {

    }

    public void send(String zimMetricsTopic, String metrics) {

    }

    public void send(String msgTopic, String name, String key, RemotingCommand command) {

    }

    public void send(String userEventTopic, String type, String key, UserEvent userEvent) {

    }
}
