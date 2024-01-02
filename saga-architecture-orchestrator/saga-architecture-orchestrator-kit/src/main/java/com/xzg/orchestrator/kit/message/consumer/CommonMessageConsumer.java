package com.xzg.orchestrator.kit.message.consumer;

import com.xzg.orchestrator.kit.message.consumer.kafka.KafkaMessageHandler;
import com.xzg.orchestrator.kit.message.consumer.kafka.KafkaSubscription;

import java.util.Set;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: CommonMessageConsumer
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:09
 * @version: 1.0
 */
public interface CommonMessageConsumer {
    /**
     *
     */
    public void close() ;

    /**
     *
     * @param subscriberId
     * @param channels
     * @param handler
     * @return
     */
    public KafkaSubscription subscribe(String subscriberId, Set<String> channels, KafkaMessageHandler handler);

}


    