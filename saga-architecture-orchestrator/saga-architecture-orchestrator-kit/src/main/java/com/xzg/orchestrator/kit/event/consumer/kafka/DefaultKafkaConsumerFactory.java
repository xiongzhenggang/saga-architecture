package com.xzg.orchestrator.kit.event.consumer.kafka;

import java.util.Properties;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: DefaultKafkaConsumerFactory
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:41
 * @version: 1.0
 */
public class DefaultKafkaConsumerFactory implements KafkaConsumerFactory {

    @Override
    public KafkaMessageConsumer makeConsumer(String subscriptionId, Properties consumerProperties) {
        return DefaultKafkaMessageConsumer.create(consumerProperties);
    }
}

    