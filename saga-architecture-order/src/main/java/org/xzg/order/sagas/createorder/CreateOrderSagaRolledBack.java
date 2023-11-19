package org.xzg.order.sagas.createorder;


import com.xzg.orchestrator.kit.event.DomainEvent;

public class CreateOrderSagaRolledBack implements DomainEvent {

  private long orderId;

  public CreateOrderSagaRolledBack() {
  }

  public CreateOrderSagaRolledBack(long orderId) {
    this.orderId = orderId;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }


}
