package com.xzg.orchestrator.kit.event.consumer.kafka;

import com.xzg.orchestrator.kit.event.consumer.KeyValue;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: EventuateKafkaMultiMessageHeader
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:29
 * @version: 1.0
 */
public class EventuateKafkaMultiMessageHeader extends KeyValue {

    public EventuateKafkaMultiMessageHeader(String key, String value) {
        super(key, value);
    }
}

    