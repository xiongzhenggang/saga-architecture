//package com.xzg.orchestrator.kit.event.consumer;
//
//import com.xzg.orchestrator.kit.event.MessageHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * @projectName: saga-architecture
// * @package: com.xzg.orchestrator.kit.event.consumer
// * @className: MessageConsumerImpl
// * @author: xzg
// * @description: TODO
// * @date: 19/11/2023-下午 12:27
// * @version: 1.0
// */
//public final class MessageConsumerImpl implements MessageConsumer {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    // This could be implemented as Around advice
//
//    private ChannelMapping channelMapping;
//    private MessageConsumerImplementation target;
//    private DecoratedMessageHandlerFactory decoratedMessageHandlerFactory;
//
//    public MessageConsumerImpl(ChannelMapping channelMapping,
//                               MessageConsumerImplementation target,
//                               DecoratedMessageHandlerFactory decoratedMessageHandlerFactory) {
//        this.channelMapping = channelMapping;
//        this.target = target;
//        this.decoratedMessageHandlerFactory = decoratedMessageHandlerFactory;
//    }
//
//    @Override
//    public MessageSubscription subscribe(String subscriberId, Set<String> channels, MessageHandler handler) {
//        logger.info("Subscribing: subscriberId = {}, channels = {}", subscriberId, channels);
//
//        Consumer<SubscriberIdAndMessage> decoratedHandler = decoratedMessageHandlerFactory.decorate(handler);
//
//        MessageSubscription messageSubscription = target.subscribe(subscriberId,
//                channels.stream().map(channelMapping::transform).collect(Collectors.toSet()),
//                message -> decoratedHandler.accept(new SubscriberIdAndMessage(subscriberId, message)));
//
//        logger.info("Subscribed: subscriberId = {}, channels = {}", subscriberId, channels);
//
//        return messageSubscription;
//    }
//
//    @Override
//    public String getId() {
//        return target.getId();
//    }
//
//    @Override
//    public void close() {
//        logger.info("Closing consumer");
//
//        target.close();
//
//        logger.info("Closed consumer");
//    }
//
//}
//
//
