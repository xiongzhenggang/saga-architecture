package com.xzg.order.config;

import com.xzg.orchestrator.kit.command.CommandDispatcher;
import com.xzg.orchestrator.kit.orchestration.saga.SagaInstanceFactory;
import com.xzg.orchestrator.kit.participant.SagaCommandDispatcherFactory;
import com.xzg.order.dao.OrderRepository;
import com.xzg.order.domain.OrderDao;
import com.xzg.order.sagas.createorder.CreateOrderSaga;
import com.xzg.order.sagas.participants.proxy.CustomerServiceProxy;
import com.xzg.order.service.OrderSagaService;
import com.xzg.order.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.xzg.order.service.OrderCommandHandler;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.xzg.order")
@EntityScan(basePackages = "com.xzg.order")
@ComponentScan("com.xzg")
public class OrderConfiguration {

  @Resource
  private SagaCommandDispatcherFactory sagaCommandDispatcherFactory;
//  @Bean
//  public CreateOrderSaga createOrderSaga(DomainEventPublisher domainEventPublisher) {
//    return new CreateOrderSaga()
//    {
//      @Autowired
//      private ApplicationEventPublisher applicationEventPublisher;
//      @Override
//      public void onStarting(String sagaId, CreateOrderSagaData createOrderSagaData) {
//        applicationEventPublisher.publishEvent(new SagaStartedEvent(this, sagaId));
//      }
//      @Override
//      public void onSagaFailed(String sagaId, CreateOrderSagaData createOrderSagaData) {
//        applicationEventPublisher.publishEvent(new SagaFailedEvent(this, sagaId));
//      }
//    }
    ;
//  }
//  @Bean
//  public LocalCreateOrderSaga localCreateOrderSaga(DomainEventPublisher domainEventPublisher, OrderDao orderDao) {
//    return new LocalCreateOrderSaga(domainEventPublisher, orderDao);
//  }
  @Bean
  public OrderSagaService orderSagaService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
    return new OrderSagaService(orderRepository, sagaInstanceFactory, createOrderSaga);
  }
  @Bean
  public CreateOrderSaga createOrderSaga(OrderService orderService, CustomerServiceProxy customerService) {
    return new CreateOrderSaga(orderService, customerService);
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
