package com.xzg.orchestrator.kit.event.consumer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: SwimlaneDispatcherBacklog
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:06
 * @version: 1.0
 */
public class SwimlaneDispatcherBacklog implements MessageConsumerBacklog {
    private final LinkedBlockingQueue<?> queue;

    public SwimlaneDispatcherBacklog(LinkedBlockingQueue<?> queue) {
        this.queue = queue;
    }

    @Override
    public int size() {
        return queue.size();
    }
}

    