//package com.xzg.orchestrator.kit.event;
//
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Optional;
//import java.util.function.Supplier;
//
//public class UpdatingOptionsBuilder {
//
//  private CommandMessage commandMessage;
//  private HashMap<Class<Throwable>, Supplier<Message>> exceptionHandlers= new LinkedHashMap<>();
//
//  public UpdatingOptionsBuilder(CommandMessage commandMessage) {
//    this.commandMessage = commandMessage;
//  }
//
//  public static <T> UpdatingOptionsBuilder replyingTo(CommandMessage<T> cm) {
//    return new UpdatingOptionsBuilder(cm);
//  }
//
//  public <E extends Throwable> UpdatingOptionsBuilder catching(Class<E> exception, Supplier<Message> replySupplier) {
//    exceptionHandlers.put((Class<Throwable>) exception, replySupplier);
//    return this;
//  }
//
//  public Optional<UpdateOptions> build() {
//
//    return Optional.of(new UpdateOptions()
//            .withIdempotencyKey(commandMessage.getMessageId())
//            .withInterceptor(new CommandMessageAggregateRepositoryInterceptor(commandMessage)));
//
//  }
//}
