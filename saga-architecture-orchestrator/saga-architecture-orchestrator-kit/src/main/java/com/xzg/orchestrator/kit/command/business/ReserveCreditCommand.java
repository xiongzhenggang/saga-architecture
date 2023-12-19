package com.xzg.orchestrator.kit.command.business;


import com.xzg.library.config.infrastructure.model.Money;
import com.xzg.orchestrator.kit.command.Command;

/**
 * 信用卡
 * @author xiongzhenggang
 */
public class ReserveCreditCommand implements Command {
  private Long orderId;
  private Money orderTotal;
  private long customerId;

  public ReserveCreditCommand() {
  }

  public ReserveCreditCommand(Long customerId, Long orderId, Money orderTotal) {
    this.customerId = customerId;
    this.orderId = orderId;
    this.orderTotal = orderTotal;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(Money orderTotal) {
    this.orderTotal = orderTotal;
  }

  public Long getOrderId() {

    return orderId;
  }

  public void setOrderId(Long orderId) {

    this.orderId = orderId;
  }

  public long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }
}
