//package com.xzg.orchestrator.kit.event;
//
//import com.xzg.orchestrator.kit.common.command.Command;
//
//public class AggregateRepositoryInterceptorExceptionHandlerBuilder<T extends CommandProcessingAggregate<T, CT>, CT extends Command>  {
//
//  public AggregateRepositoryInterceptorExceptionHandlerBuilder(Class<Throwable> exceptionClass, BiFunction<T, Throwable, Optional<UpdateEventsAndOptions>> exceptionHandler) {
//  }
//
//  static <T extends CommandProcessingAggregate<T, CT>, CT extends Command, E extends Throwable>
//  AggregateRepositoryInterceptorExceptionHandlerBuilder<T, CT> catching(Class<E> exceptionClass, BiFunction<T, E, Optional<UpdateEventsAndOptions>> exceptionHandler) {
//    return new AggregateRepositoryInterceptorExceptionHandlerBuilder<>((Class<Throwable>) exceptionClass, (a, e) -> exceptionHandler.apply((T) a, (E) e));
//  }
//
//  public AggregateRepositoryInterceptor<T, CT> build() {
//    throw new UnsupportedOperationException();
//  }
//}
