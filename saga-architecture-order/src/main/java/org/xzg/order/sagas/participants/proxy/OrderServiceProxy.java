package org.xzg.order.sagas.participants.proxy;


import com.xzg.orchestrator.kit.command.Success;
import com.xzg.orchestrator.kit.dsl.CommandEndpoint;
import com.xzg.orchestrator.kit.dsl.CommandEndpointBuilder;
import com.xzg.orchestrator.kit.command.business.ApproveOrderCommand;
import org.xzg.order.service.RejectOrderCommand;

public class OrderServiceProxy {

  public final CommandEndpoint<RejectOrderCommand> reject = CommandEndpointBuilder
          .forCommand(RejectOrderCommand.class)
          .withChannel("orderService")
          .withReply(Success.class)
          .build();

  public final CommandEndpoint<ApproveOrderCommand> approve = CommandEndpointBuilder
          .forCommand(ApproveOrderCommand.class)
          .withChannel("orderService")
          .withReply(Success.class)
          .build();

}
