package com.xzg.orchestrator.kit.event.consumer.kafka;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: EventuateKafkaConsumerState
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:17
 * @version: 1.0
 */
public enum EventuateKafkaConsumerState {
    MESSAGE_HANDLING_FAILED, STARTED, FAILED_TO_START, STOPPED, FAILED, CREATED
}


    