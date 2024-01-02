package com.xzg.orchestrator.kit.participant;


import com.xzg.orchestrator.kit.common.LockTarget;
import com.xzg.orchestrator.kit.command.PathVariables;
import com.xzg.orchestrator.kit.message.CommandMessage;
import com.xzg.orchestrator.kit.message.Message;

public interface PostLockFunction<C> {

   LockTarget apply(CommandMessage<C> cm, PathVariables pvs, Message reply);
}
