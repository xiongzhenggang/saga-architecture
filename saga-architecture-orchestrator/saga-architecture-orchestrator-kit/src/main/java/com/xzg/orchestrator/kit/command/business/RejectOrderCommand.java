package com.xzg.orchestrator.kit.command.business;


import com.xzg.orchestrator.kit.command.Command;

/**
 * 订单拒绝命令
 * @author xiongzhenggang
 */
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
