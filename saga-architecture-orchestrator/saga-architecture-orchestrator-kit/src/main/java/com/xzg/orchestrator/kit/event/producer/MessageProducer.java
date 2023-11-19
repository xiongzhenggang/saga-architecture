package com.xzg.orchestrator.kit.event.producer;

import com.xzg.orchestrator.kit.event.Message;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event
 * @className: MessageProducer
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 12:21
 * @version: 1.0
 */

public interface MessageProducer {

    /**
     * Send a message
     * @param destination the destination channel
     * @param message the message to doSend
     * @see Message
     */
    void send(String destination, Message message);

}
    