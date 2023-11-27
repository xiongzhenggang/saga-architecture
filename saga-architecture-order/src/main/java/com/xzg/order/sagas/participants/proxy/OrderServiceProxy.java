package com.xzg.order.sagas.participants.proxy;


import com.xzg.orchestrator.kit.command.Success;
import com.xzg.orchestrator.kit.common.SagaServiceEnum;
import com.xzg.orchestrator.kit.dsl.CommandEndpoint;
import com.xzg.orchestrator.kit.dsl.CommandEndpointBuilder;
import com.xzg.orchestrator.kit.command.business.ApproveOrderCommand;
import com.xzg.order.service.RejectOrderCommand;

public class OrderServiceProxy {

  public final CommandEndpoint<RejectOrderCommand> reject = CommandEndpointBuilder
          .forCommand(RejectOrderCommand.class)
          .withChannel(SagaServiceEnum.ORDER_SERVICE.getType())
          .withReply(Success.class)
          .build();

  public final CommandEndpoint<ApproveOrderCommand> approve = CommandEndpointBuilder
          .forCommand(ApproveOrderCommand.class)
          .withChannel(SagaServiceEnum.ORDER_SERVICE.getType())
          .withReply(Success.class)
          .build();

}
