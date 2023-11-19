//package com.xzg.orchestrator.kit.event.consumer;
//
//import com.xzg.orchestrator.kit.event.MessageImpl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Set;
//
///**
// * @projectName: saga-architecture
// * @package: com.xzg.orchestrator.kit.event.consumer
// * @className: EventuateTramKafkaMessageConsumer
// * @author: xzg
// * @description: TODO
// * @date: 19/11/2023-下午 12:26
// * @version: 1.0
// */
//public class EventuateTramKafkaMessageConsumer implements MessageConsumerImplementation {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    private MessageConsumerKafkaImpl messageConsumerKafka;
//
//    public EventuateTramKafkaMessageConsumer(MessageConsumerKafkaImpl messageConsumerKafka) {
//        this.messageConsumerKafka = messageConsumerKafka;
//    }
//
//    @Override
//    public MessageSubscription subscribe(String subscriberId, Set<String> channels, MessageHandler handler) {
//        logger.info("Subscribing: subscriberId = {}, channels = {}", subscriberId, channels);
//
//        KafkaSubscription subscription = messageConsumerKafka.subscribe(subscriberId,
//                channels, message -> handler.accept(JSonMapper.fromJson(message.getPayload(), MessageImpl.class)));
//
//        logger.info("Subscribed: subscriberId = {}, channels = {}", subscriberId, channels);
//
//        return subscription::close;
//    }
//
//    @Override
//    public String getId() {
//        return messageConsumerKafka.getId();
//    }
//
//    @Override
//    public void close() {
//        logger.info("Closing consumer");
//
//        messageConsumerKafka.close();
//
//        logger.info("Closed consumer");
//    }
//}