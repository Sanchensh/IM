package com.mrxu.server.rpc;

import com.mrxu.common.ConnectionEventType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConnectionEventListener {

    private ConcurrentHashMap<ConnectionEventType, List<ConnectionEventProcessor>> processors = new ConcurrentHashMap<ConnectionEventType, List<ConnectionEventProcessor>>(
            3);

    /**
     * Dispatch events.
     *
     * @param type
     * @param conn
     */
    public void onEvent(ConnectionEventType type, Connection conn) {
        List<ConnectionEventProcessor> processorList = this.processors.get(type);
        if (processorList != null) {
            for (ConnectionEventProcessor processor : processorList) {
                processor.onEvent(conn);
            }
        }
    }

    /**
     * Add event processor.
     *
     * @param type
     * @param processor
     */
    public void addConnectionEventProcessor(ConnectionEventType type,
                                            ConnectionEventProcessor processor) {
        List<ConnectionEventProcessor> processorList = this.processors.get(type);
        if (processorList == null) {
            this.processors.putIfAbsent(type, new ArrayList<ConnectionEventProcessor>(1));
            processorList = this.processors.get(type);
        }
        processorList.add(processor);
    }

}
