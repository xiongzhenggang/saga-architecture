package com.xzg.orchestrator.kit.event.consumer.kafka;

import com.xzg.orchestrator.kit.event.MessageImpl;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: KafkaMessage
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:08
 * @version: 1.0
 */
public class KafkaMessage extends MessageImpl {
    private String payload;

    public KafkaMessage(String payload) {
        this.payload = payload;
    }

    @Override
    public String getPayload() {
        return payload;
    }
}


    