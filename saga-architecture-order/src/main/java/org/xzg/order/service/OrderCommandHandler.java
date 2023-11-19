package org.xzg.order.service;


import com.xzg.orchestrator.kit.command.CommandHandlers;
import com.xzg.orchestrator.kit.event.CommandMessage;
import com.xzg.orchestrator.kit.event.Message;
import lombok.Data;
import org.xzg.order.domain.Order;
import org.xzg.order.domain.OrderDao;
import com.xzg.orchestrator.kit.command.business.ApproveOrderCommand;

import static com.xzg.orchestrator.kit.command.CommandHandlerReplyBuilder.withSuccess;
import static com.xzg.orchestrator.kit.participant.SagaCommandHandlersBuilder.fromChannel;

@Data
public class OrderCommandHandler {

  private OrderDao orderDao;

  public OrderCommandHandler(OrderDao orderDao) {
    this.orderDao = orderDao;
  }

  public CommandHandlers commandHandlerDefinitions() {
    return    
            fromChannel("orderService")
            .onMessage(ApproveOrderCommand.class, this::approve)
            .onMessage(RejectOrderCommand.class, this::reject)
            .build();
  }

  public Message approve(CommandMessage<ApproveOrderCommand> cm) {
    long orderId = cm.getCommand().getOrderId();
    Order order = orderDao.findById(orderId);
    order.noteCreditReserved();
    return withSuccess();
  }

  public Message reject(CommandMessage<RejectOrderCommand> cm) {
    long orderId = cm.getCommand().getOrderId();
    Order order = orderDao.findById(orderId);
    order.noteCreditReservationFailed();
    return withSuccess();
  }

}
