package org.xzg.order.sagas.createorder;


import com.xzg.orchestrator.kit.event.DomainEvent;

public class CreateOrderSagaCompletedSuccesfully implements DomainEvent {
  private long orderId;

  public CreateOrderSagaCompletedSuccesfully() {
  }

  public CreateOrderSagaCompletedSuccesfully(long orderId) {
    this.orderId = orderId;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }


}
