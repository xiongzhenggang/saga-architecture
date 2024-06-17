package com.xzg.order.sagas.config;

import com.xzg.orchestrator.kit.command.CommandDispatcher;
import com.xzg.orchestrator.kit.command.service.CommandProducer;
import com.xzg.orchestrator.kit.message.consumer.CommonMessageConsumer;
import com.xzg.orchestrator.kit.orchestration.saga.Saga;
import com.xzg.orchestrator.kit.orchestration.saga.SagaCommandProducer;
import com.xzg.orchestrator.kit.orchestration.saga.SagaInstanceFactory;
import com.xzg.orchestrator.kit.orchestration.saga.SagaManagerFactory;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaInstanceRepository;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaMessageRepository;
import com.xzg.orchestrator.kit.participant.SagaCommandDispatcherFactory;
import com.xzg.order.dao.OrderRepository;
import com.xzg.order.domain.OrderDao;
import com.xzg.order.sagas.createorder.CreateOrderSaga;
import com.xzg.order.sagas.participants.proxy.AccountServiceProxy;
import com.xzg.order.sagas.participants.proxy.GoodsServiceProxy;
import com.xzg.order.sagas.service.OrderSagaService;
import com.xzg.order.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.xzg.order.service.OrderCommandHandler;

import java.util.Collection;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.xzg.order")
@EntityScan(basePackages = "com.xzg.order")
@ComponentScan("com.xzg")
public class OrderConfiguration {

  @Resource
  private SagaCommandDispatcherFactory sagaCommandDispatcherFactory;
  @Resource
  private SagaInstanceRepository sagaInstanceRepository;

  @Resource
  private SagaMessageRepository sagaMessageRepository;

  @Bean
  public OrderSagaService orderSagaService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
    return new OrderSagaService(orderRepository, sagaInstanceFactory, createOrderSaga);
  }
  @Bean
  public CreateOrderSaga createOrderSaga(OrderService orderService, AccountServiceProxy customerService, GoodsServiceProxy goodsServiceProxy) {
    return new CreateOrderSaga(orderService, customerService,goodsServiceProxy);
  }
  @Bean
  public OrderCommandHandler orderCommandHandler(OrderDao orderDao) {
    return new OrderCommandHandler(orderDao);
  }

  @Bean
  public CommandDispatcher orderCommandDispatcher(OrderCommandHandler target) {
    return sagaCommandDispatcherFactory.make("orderCommandDispatcher", target.commandHandlerDefinitions());
  }

  /**
   * saga 实例处理的配置
   */
  /**
   *
   * @param commandProducer
   * @param messageConsumer
   * @param sagaCommandProducer
   * @param sagas 本工程当前只在order服务定义了CreateOrderSaga
   * @return
   */
  @Bean
  public SagaInstanceFactory sagaInstanceFactory(CommandProducer commandProducer,
                                                 CommonMessageConsumer messageConsumer,
                                                 SagaCommandProducer sagaCommandProducer,
                                                 Collection<Saga<?>> sagas) {
    SagaManagerFactory smf = new SagaManagerFactory(sagaInstanceRepository,sagaMessageRepository, commandProducer, messageConsumer, sagaCommandProducer);
    return new SagaInstanceFactory(smf, sagas);
  }
}
