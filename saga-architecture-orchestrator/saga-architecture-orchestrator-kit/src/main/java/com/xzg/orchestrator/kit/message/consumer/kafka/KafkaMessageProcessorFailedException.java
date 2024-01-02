package com.xzg.orchestrator.kit.message.consumer.kafka;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: KafkaMessageProcessorFailedException
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:25
 * @version: 1.0
 */
public class KafkaMessageProcessorFailedException extends RuntimeException {
    public KafkaMessageProcessorFailedException(Throwable t) {
        super(t);
    }
}

    