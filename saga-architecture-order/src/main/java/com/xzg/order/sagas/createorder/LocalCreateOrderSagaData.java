package com.xzg.order.sagas.createorder;


import com.xzg.order.service.OrderDetails;

public class LocalCreateOrderSagaData {

  private Long orderId;

  private OrderDetails orderDetails;

  public LocalCreateOrderSagaData() {
  }

  public LocalCreateOrderSagaData(OrderDetails orderDetails) {
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
