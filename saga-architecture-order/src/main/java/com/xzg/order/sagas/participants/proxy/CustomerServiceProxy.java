package com.xzg.order.sagas.participants.proxy;


import com.xzg.library.config.infrastructure.model.Money;
import com.xzg.orchestrator.kit.command.CommandWithDestination;
import com.xzg.orchestrator.kit.command.business.ReserveCreditCommand;
import com.xzg.orchestrator.kit.common.SagaServiceEnum;
import org.springframework.stereotype.Component;

import static com.xzg.orchestrator.kit.command.CommandWithDestinationBuilder.send;

@Component
public class CustomerServiceProxy {

//  public final CommandEndpoint<ReserveCreditCommand> reserveCredit = CommandEndpointBuilder
//            .forCommand(ReserveCreditCommand.class)
//            .withChannel("customerService")
//            .withReply(Success.class)
//            .build();
public CommandWithDestination reserveCredit(long orderId, Long customerId, Money orderTotal) {
  return send(new ReserveCreditCommand(customerId, orderId, orderTotal))
          .to(SagaServiceEnum.ACCOUNT_SERVICE.getType())
          .build();
}
}
