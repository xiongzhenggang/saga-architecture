package com.xzg.orchestrator.kit.message.consumer;

import java.util.Properties;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: ConsumerPropertiesFactory
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:19
 * @version: 1.0
 */
public class ConsumerPropertiesFactory {
    public static Properties makeDefaultConsumerProperties(String bootstrapServers, String subscriberId) {
        Properties consumerProperties = new Properties();
        consumerProperties.put("bootstrap.servers", bootstrapServers);
        consumerProperties.put("group.id", subscriberId);
        consumerProperties.put("enable.auto.commit", "false");
        //consumerProperties.put("auto.commit.interval.ms", "1000");
        consumerProperties.put("session.timeout.ms", "30000");
        consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        consumerProperties.put("auto.offset.reset", "earliest");
        return consumerProperties;
    }
}

    