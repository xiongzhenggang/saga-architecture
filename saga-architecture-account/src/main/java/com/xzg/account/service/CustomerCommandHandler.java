package com.xzg.account.service;


import com.xzg.account.domain.Customer;
import com.xzg.account.domain.CustomerCreditLimitExceededException;
import com.xzg.account.domain.CustomerDao;
import com.xzg.orchestrator.kit.command.CommandHandlers;
import com.xzg.orchestrator.kit.command.business.ReserveCreditCommand;
import com.xzg.orchestrator.kit.common.SagaServiceEnum;
import com.xzg.orchestrator.kit.event.CommandMessage;
import com.xzg.orchestrator.kit.event.Message;
import com.xzg.orchestrator.kit.participant.SagaCommandHandlersBuilder;

import static com.xzg.orchestrator.kit.command.CommandHandlerReplyBuilder.withFailure;
import static com.xzg.orchestrator.kit.command.CommandHandlerReplyBuilder.withSuccess;

public class CustomerCommandHandler {

  private CustomerDao customerDao;

  public CustomerCommandHandler(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  /**
   * 定义命令
   * @return
   */
  public CommandHandlers commandHandlerDefinitions() {
    return SagaCommandHandlersBuilder
            .fromChannel(SagaServiceEnum.ACCOUNT_SERVICE.getType())
            .onMessage(ReserveCreditCommand.class, this::reserveCredit)
            .build();
  }

  public Message reserveCredit(CommandMessage<ReserveCreditCommand> cm) {
    ReserveCreditCommand cmd = cm.getCommand();
    long customerId = cmd.getCustomerId();
    Customer customer = customerDao.findById(customerId);
    // TODO null check
    try {
      customer.reserveCredit(cmd.getOrderId(), cmd.getOrderTotal());
      return withSuccess(new CustomerCreditReserved());
    } catch (CustomerCreditLimitExceededException e) {
      return withFailure(new CustomerCreditReservationFailed());
    }
  }

  // withLock(Customer.class, customerId).
  // TODO @Validate to trigger validation and error reply


}
