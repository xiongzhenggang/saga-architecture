package com.xzg.order.service;


import com.xzg.orchestrator.kit.command.Command;

public class RejectOrderCommand implements Command {

  private long orderId;

  private RejectOrderCommand() {
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public RejectOrderCommand(long orderId) {

    this.orderId = orderId;
  }

  public long getOrderId() {
    return orderId;
  }
}
