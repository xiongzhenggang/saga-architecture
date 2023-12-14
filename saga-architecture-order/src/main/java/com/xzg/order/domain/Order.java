package com.xzg.order.domain;


import com.xzg.orchestrator.kit.business.enums.RejectionReason;
import jakarta.persistence.*;
import lombok.Data;
import com.xzg.order.service.OrderDetails;

@Data
@Entity
@Table(name="orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private OrderState state;

  @Embedded
  private OrderDetails orderDetails;

  @Enumerated(EnumType.STRING)
  private RejectionReason rejectionReason;

  @Version
  private Long version;
  public Order() {
  }

  public Order(OrderDetails orderDetails) {
    this.orderDetails = orderDetails;
    this.state = OrderState.PENDING;
  }

  public static Order createOrder(OrderDetails orderDetails) {
      return new Order(orderDetails);
  }

  public Long getId() {
    return id;
  }
  public void approve() {
    this.state = OrderState.APPROVED;
  }
  public void noteCreditReserved() {
    this.state = OrderState.APPROVED;
  }

  public void noteCreditReservationFailed() {
    this.state = OrderState.REJECTED;
  }

  public OrderState getState() {
    return state;
  }

  public void reject(RejectionReason rejectionReason) {
    this.state = OrderState.REJECTED;
    this.rejectionReason = rejectionReason;
  }
  public RejectionReason getRejectionReason() {
    return rejectionReason;
  }
}
