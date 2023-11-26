package com.xzg.orchestrator.kit.event.consumer.kafka;

import java.util.Properties;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: KafkaConsumerFactory
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:16
 * @version: 1.0
 */
public interface KafkaConsumerFactory {

    KafkaMessageConsumer makeConsumer(String subscriptionId, Properties consumerProperties);

}

    