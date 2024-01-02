package com.xzg.orchestrator.kit.message.consumer.kafka;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: KafkaSubscription
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:04
 * @version: 1.0
 */
public class KafkaSubscription {
    private Runnable closingCallback;

    public KafkaSubscription(Runnable closingCallback) {
        this.closingCallback = closingCallback;
    }

    public void close() {
        closingCallback.run();
    }
}

    