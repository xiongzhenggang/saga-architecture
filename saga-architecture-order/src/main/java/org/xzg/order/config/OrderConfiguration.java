package org.xzg.order.config;

import com.xzg.orchestrator.kit.command.CommandDispatcher;
import com.xzg.orchestrator.kit.participant.SagaCommandDispatcherFactory;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.xzg.order.domain.OrderDao;
import org.xzg.order.event.DomainEventPublisher;
import org.xzg.order.sagas.createorder.CreateOrderSaga;
import org.xzg.order.sagas.createorder.CreateOrderSagaData;
import org.xzg.order.sagas.createorder.LocalCreateOrderSaga;
import org.xzg.order.service.OrderCommandHandler;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@EntityScan("com.xzg")
@ComponentScan
//@Import(OptimisticLockingDecoratorConfiguration.class)
public class OrderConfiguration {

  @Resource
  private SagaCommandDispatcherFactory sagaCommandDispatcherFactory;
  @Bean
  public CreateOrderSaga createOrderSaga(DomainEventPublisher domainEventPublisher) {
    return new CreateOrderSaga(domainEventPublisher) {
      @Autowired
      private ApplicationEventPublisher applicationEventPublisher;

      @Override
      public void onStarting(String sagaId, CreateOrderSagaData createOrderSagaData) {
        applicationEventPublisher.publishEvent(new SagaStartedEvent(this, sagaId));
      }

      @Override
      public void onSagaFailed(String sagaId, CreateOrderSagaData createOrderSagaData) {
        applicationEventPublisher.publishEvent(new SagaFailedEvent(this, sagaId));
      }
    };
  }

  @Bean
  public LocalCreateOrderSaga localCreateOrderSaga(DomainEventPublisher domainEventPublisher, OrderDao orderDao) {
    return new LocalCreateOrderSaga(domainEventPublisher, orderDao);
  }

  @Bean
  public OrderCommandHandler orderCommandHandler(OrderDao orderDao) {
    return new OrderCommandHandler(orderDao);
  }

  @Bean
  public CommandDispatcher orderCommandDispatcher(OrderCommandHandler target) {
    return sagaCommandDispatcherFactory.make("orderCommandDispatcher", target.commandHandlerDefinitions());
  }

}
