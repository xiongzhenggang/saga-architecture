//package com.xzg.orchestrator.kit.event.consumer.kafka;
//
//import org.springframework.kafka.core.ConsumerFactory;
//
//
///**
// * @projectName: saga-architecture
// * @package: com.xzg.orchestrator.kit.event.consumer
// * @className: DefaultKafkaConsumerFactory
// * @author: xzg
// * @description: TODO
// * @date: 22/11/2023-下午 10:41
// * @version: 1.0
// */
//public class DefaultKafkaConsumerFactory implements KafkaConsumerFactory {
//
//    @Override
//    public KafkaMessageConsumer makeConsumer(ConsumerFactory<String, byte[]> consumerFactory) {
//        return DefaultKafkaMessageConsumer.create(consumerFactory);
//    }
//}
//
//