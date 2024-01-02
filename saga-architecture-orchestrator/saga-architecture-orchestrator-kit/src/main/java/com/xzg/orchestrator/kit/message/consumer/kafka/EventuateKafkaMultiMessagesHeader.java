package com.xzg.orchestrator.kit.message.consumer.kafka;

import com.xzg.orchestrator.kit.message.consumer.KeyValue;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: EventuateKafkaMultiMessagesHeader
 * @author: xzg
 * @description: TODO
 * @date: 24/11/2023-下午 7:47
 * @version: 1.0
 */
public class EventuateKafkaMultiMessagesHeader extends KeyValue {

    public EventuateKafkaMultiMessagesHeader(String key, String value) {
        super(key, value);
    }
}

    