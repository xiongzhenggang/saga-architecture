package com.xzg.order.service;


import com.xzg.orchestrator.kit.command.CommandHandlers;
import com.xzg.orchestrator.kit.command.business.ApproveOrderCommand;
import com.xzg.orchestrator.kit.command.business.RejectOrderCommand;
import com.xzg.orchestrator.kit.common.SagaServiceEnum;
import com.xzg.orchestrator.kit.message.CommandMessage;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.order.domain.Order;
import com.xzg.order.domain.OrderDao;
import lombok.Data;

import static com.xzg.orchestrator.kit.command.CommandHandlerReplyBuilder.withSuccess;
import static com.xzg.orchestrator.kit.participant.SagaCommandHandlersBuilder.fromChannel;

/**
 * @author xiongzhenggang
 */
@Data
public class OrderCommandHandler {

    private OrderDao orderDao;

    public OrderCommandHandler(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * 定义正向流程中 order->goods->account->成功完成order
     * 定义补偿流程中 order->goods失败->补偿order 或者 order->goods->account失败->补偿goods->补偿order
     *
     * @return
     */
    public CommandHandlers commandHandlerDefinitions() {
        return
                fromChannel(SagaServiceEnum.ORDER_SERVICE.getType())
                        .onMessage(ApproveOrderCommand.class, this::approve)
                        .onMessage(RejectOrderCommand.class, this::reject)
                        .build();
    }

    /**
     * 订单状态完成
     * @param cm
     * @return
     */
    public Message approve(CommandMessage<ApproveOrderCommand> cm) {
        long orderId = cm.getCommand().getOrderId();
        Order order = orderDao.findById(orderId);
        order.noteCreditReserved();
        return withSuccess();
    }

    /**
     * 订单状态失败
     * @param cm
     * @return
     */
    public Message reject(CommandMessage<RejectOrderCommand> cm) {
        long orderId = cm.getCommand().getOrderId();
        Order order = orderDao.findById(orderId);
        order.noteCreditReservationFailed();
        return withSuccess();
    }

}
