package com.xzg.account.service;


import com.xzg.account.domain.Customer;
import com.xzg.account.domain.CustomerDao;
import com.xzg.account.exception.CustomerCreditLimitExceededException;
import com.xzg.orchestrator.kit.participant.result.CustomerCreditLimitExceeded;
import com.xzg.orchestrator.kit.participant.result.CustomerCreditReserved;
import com.xzg.orchestrator.kit.participant.result.CustomerNotFound;
import com.xzg.orchestrator.kit.command.CommandHandlers;
import com.xzg.orchestrator.kit.command.business.ReserveCreditCommand;
import com.xzg.orchestrator.kit.common.SagaServiceEnum;
import com.xzg.orchestrator.kit.event.CommandMessage;
import com.xzg.orchestrator.kit.event.Message;
import com.xzg.orchestrator.kit.participant.SagaCommandHandlersBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.xzg.orchestrator.kit.command.CommandHandlerReplyBuilder.withFailure;
import static com.xzg.orchestrator.kit.command.CommandHandlerReplyBuilder.withSuccess;

/**
 * @author xiongzhenggang
 * @Date 2023/12/19
 */
@Slf4j
public class CustomerCommandHandler {

    private CustomerDao customerDao;

    public CustomerCommandHandler(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * 此处只定义了接受成功的响应，因为是最后一步，没有补偿情况
     * 发送到order
     *
     * @return
     */
    public CommandHandlers commandHandlerDefinitions() {
        return SagaCommandHandlersBuilder
                .fromChannel(SagaServiceEnum.ACCOUNT_SERVICE.getType())
                .onMessage(ReserveCreditCommand.class, this::reserveCredit)
                .build();
    }

    /**
     * 账户信用卡是否够用
     *
     * @param cm
     * @return
     */
    public Message reserveCredit(CommandMessage<ReserveCreditCommand> cm) {
        ReserveCreditCommand cmd = cm.getCommand();
        long customerId = cmd.getCustomerId();
        Customer customer = customerDao.findById(customerId);
        if (Objects.isNull(customer)) {
            return withFailure(new CustomerNotFound());
        }
        try {
            customer.reserveCredit(cmd.getOrderId(), cmd.getOrderTotal());
            //账户余额扣减
            customer.setUpdateTime(LocalDateTime.now());
            customerDao.save(customer);
            return withSuccess(new CustomerCreditReserved());
        } catch (CustomerCreditLimitExceededException e) {
            log.error("订单余额不足：{}", e);
            return withFailure(new CustomerCreditLimitExceeded());
        }
    }


}
