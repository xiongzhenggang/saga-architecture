package com.xzg.orchestrator.kit.command.business;


import com.xzg.orchestrator.kit.command.Command;

public class ApproveOrderCommand implements Command {
  private long orderId;

  private ApproveOrderCommand() {
  }


  public ApproveOrderCommand(long orderId) {

    this.orderId = orderId;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }
}
