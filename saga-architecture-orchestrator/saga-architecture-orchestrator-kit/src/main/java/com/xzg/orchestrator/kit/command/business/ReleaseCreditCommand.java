package com.xzg.orchestrator.kit.command.business;


import com.xzg.orchestrator.kit.command.Command;

/**
 * @author xiongzhenggang
 */
public class ReleaseCreditCommand implements Command {
    private long orderId;

    private ReleaseCreditCommand() {
    }


    public ReleaseCreditCommand(long orderId) {

        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
