package com.xzg.orchestrator.kit.message.consumer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: RawKafkaMessage
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:05
 * @version: 1.0
 */
public class RawKafkaMessage {
    private byte[] payload;

    public RawKafkaMessage(byte[] payload) {
        this.payload = payload;
    }

    public byte[] getPayload() {
        return payload;
    }
}


    