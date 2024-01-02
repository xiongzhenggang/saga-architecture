package com.xzg.orchestrator.kit.message.consumer.kafka;

import com.xzg.orchestrator.kit.message.consumer.MessageConsumerBacklog;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: EventuateKafkaConsumerMessageHandler
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:18
 * @version: 1.0
 */
public interface EventuateKafkaConsumerMessageHandler
        extends BiFunction<ConsumerRecord<String, byte[]>, BiConsumer<Void, Throwable>, MessageConsumerBacklog> {
}
    