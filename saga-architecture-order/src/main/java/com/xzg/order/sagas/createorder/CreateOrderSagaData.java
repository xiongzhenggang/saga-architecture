package com.xzg.order.sagas.createorder;


import com.xzg.orchestrator.kit.common.enums.RejectionReason;
import lombok.Data;
import com.xzg.order.service.OrderDetails;

@Data
public class CreateOrderSagaData  {

  private Long orderId;

  private OrderDetails orderDetails;

  private RejectionReason rejectionReason;
  public CreateOrderSagaData() {
  }

  public CreateOrderSagaData(Long orderId, OrderDetails orderDetails) {
    this.orderId = orderId;
    this.orderDetails = orderDetails;
  }

  public CreateOrderSagaData(OrderDetails orderDetails) {
    this.orderDetails = orderDetails;
  }

  public Long getOrderId() {
    return orderId;
  }

  public OrderDetails getOrderDetails() {
    return orderDetails;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }
}
