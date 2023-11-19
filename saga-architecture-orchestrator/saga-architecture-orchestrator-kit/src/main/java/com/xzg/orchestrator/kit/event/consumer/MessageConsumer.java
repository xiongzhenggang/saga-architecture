package com.xzg.orchestrator.kit.event.consumer;

import com.xzg.orchestrator.kit.event.MessageHandler;

import java.util.Set;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: MessageConsumer
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 2:47
 * @version: 1.0
 */

public interface MessageConsumer {
    void subscribe(String subscriberId, Set<String> channels, MessageHandler handler);
}


    