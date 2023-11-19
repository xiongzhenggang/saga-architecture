//package com.xzg.orchestrator.kit.event;
//
//import com.xzg.orchestrator.kit.common.command.CommandHandlerReplyBuilder;
//import com.xzg.orchestrator.kit.common.command.CommandMessageHeaders;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.CompletableFuture;
//import java.util.function.Function;
//
//import static java.util.Collections.singleton;
//import static java.util.stream.Collectors.toMap;
//
//@Service
//public class SagaReplyRequestedEventSubscriber {
//
//  @Autowired
//  private MessageProducer messageProducer;
//
//  @Autowired
//  private EventuateAggregateStore aggregateStore;
//  private String subscriberId;
//  private Set<String> aggregateTypes;
//
//  public SagaReplyRequestedEventSubscriber(String subscriberId, Set<String> aggregateTypes) {
//    this.subscriberId = subscriberId;
//    this.aggregateTypes = aggregateTypes;
//  }
//
//  @PostConstruct
//  public void subscribe() {
//    Map<String, Set<String>> aggregatesAndEvents =
//            aggregateTypes.stream().collect(toMap(Function.identity(), x -> singleton(SagaReplyRequestedEvent.class.getName())));
//
//    aggregateStore.subscribe(subscriberId,
//            aggregatesAndEvents,
//            SubscriberOptions.DEFAULTS,
//            this::sendReply);
//  }
//
//  public CompletableFuture<Object> sendReply(DispatchedEvent<Event> de) {
//    SagaReplyRequestedEvent event = (SagaReplyRequestedEvent) de.getEvent();
//    Message reply = CommandHandlerReplyBuilder.withSuccess();
//    messageProducer.send(event.getCorrelationHeaders().get(CommandMessageHeaders.inReply(CommandMessageHeaders.REPLY_TO)),
//            MessageBuilder
//                    .withMessage(reply)
//                    .withExtraHeaders("", event.getCorrelationHeaders())
//                    .build());
//    return CompletableFuture.completedFuture(null);
//  }
//}
