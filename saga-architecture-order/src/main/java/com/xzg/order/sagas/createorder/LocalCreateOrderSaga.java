//package com.xzg.order.sagas.createorder;
//
//
//import com.xzg.library.config.infrastructure.model.Money;
//import com.xzg.orchestrator.kit.command.CommandWithDestination;
//import com.xzg.orchestrator.kit.common.SagaServiceEnum;
//import com.xzg.orchestrator.kit.orchestration.dsl.SimpleSaga;
//import com.xzg.orchestrator.kit.event.ResultWithEvents;
//import com.xzg.orchestrator.kit.orchestration.saga.SagaDefinition;
//import com.xzg.order.domain.Order;
//import com.xzg.order.domain.OrderDao;
//import com.xzg.order.event.DomainEventPublisher;
//import com.xzg.orchestrator.kit.command.business.ReserveCreditCommand;
//
//import java.util.Collections;
//
//import static com.xzg.orchestrator.kit.command.CommandWithDestinationBuilder.send;
//
//
//public class LocalCreateOrderSaga implements SimpleSaga<LocalCreateOrderSagaData> {
//
//  private DomainEventPublisher domainEventPublisher;
//  private OrderDao orderDao;
//
//  public LocalCreateOrderSaga(DomainEventPublisher domainEventPublisher,
//                              OrderDao orderDao) {
//    this.domainEventPublisher = domainEventPublisher;
//    this.orderDao = orderDao;
//  }
//
//  private SagaDefinition<LocalCreateOrderSagaData> sagaDefinition =
//          step()
//            .invokeLocal(this::create)
//            .withCompensation(this::reject)
//          .step()
//            .invokeParticipant(this::reserveCredit)
//          .step()
//            .invokeLocal(this::approve)
//          .build();
//
//  @Override
//  public SagaDefinition<LocalCreateOrderSagaData> getSagaDefinition() {
//    return this.sagaDefinition;
//  }
//
//
//  private void create(LocalCreateOrderSagaData data) {
//    Order order =  Order.createOrder(data.getOrderDetails());
//    orderDao.save(order);
//    data.setOrderId(order.getId());
//  }
//
//
//  private CommandWithDestination reserveCredit(LocalCreateOrderSagaData data) {
//    long orderId = data.getOrderId();
//    Long customerId = data.getOrderDetails().getCustomerId();
//    Money orderTotal = data.getOrderDetails().getOrderTotal();
//    return send(new ReserveCreditCommand(customerId, orderId, orderTotal))
//            .to(SagaServiceEnum.ACCOUNT_SERVICE.getType())
//            .build();
//  }
//
//  public void reject(LocalCreateOrderSagaData data) {
//    orderDao.findById(data.getOrderId()).noteCreditReservationFailed();
//  }
//
//  private void approve(LocalCreateOrderSagaData data) {
//    orderDao.findById(data.getOrderId()).noteCreditReserved();
//  }
//
//  @Override
//  public void onSagaCompletedSuccessfully(String sagaId, LocalCreateOrderSagaData createOrderSagaData) {
//    domainEventPublisher.publish(LocalCreateOrderSaga.class, sagaId, Collections.singletonList(new CreateOrderSagaCompletedSuccesfully(createOrderSagaData.getOrderId())));
//  }
//
//  @Override
//  public void onSagaRolledBack(String sagaId, LocalCreateOrderSagaData createOrderSagaData) {
//    domainEventPublisher.publish(LocalCreateOrderSaga.class, sagaId, Collections.singletonList(new CreateOrderSagaRolledBack(createOrderSagaData.getOrderId())));
//  }
//}
