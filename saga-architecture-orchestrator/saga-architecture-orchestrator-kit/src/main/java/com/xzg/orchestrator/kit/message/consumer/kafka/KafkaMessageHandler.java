package com.xzg.orchestrator.kit.message.consumer.kafka;

import com.xzg.orchestrator.kit.message.MessageImpl;

import java.util.function.Consumer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: KafkaMessageHandler
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:07
 * @version: 1.0
 */
public interface KafkaMessageHandler extends Consumer<MessageImpl> {

}


    