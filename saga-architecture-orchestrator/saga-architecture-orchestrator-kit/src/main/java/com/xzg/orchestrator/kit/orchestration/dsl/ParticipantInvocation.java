package com.xzg.orchestrator.kit.orchestration.dsl;


import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.orchestration.CommandWithDestinationAndType;

public interface ParticipantInvocation<Data> {

  boolean isSuccessfulReply(Message message);

  boolean isInvocable(Data data);

  CommandWithDestinationAndType makeCommandToSend(Data data);
}
