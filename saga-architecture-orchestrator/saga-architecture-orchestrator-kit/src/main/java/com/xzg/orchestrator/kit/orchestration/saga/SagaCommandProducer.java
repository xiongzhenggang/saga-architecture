package com.xzg.orchestrator.kit.orchestration.saga;

import com.xzg.orchestrator.kit.orchestration.CommandWithDestinationAndType;

import java.util.List;

public interface SagaCommandProducer {
  String sendCommands(String sagaType, String sagaId, List<CommandWithDestinationAndType> commands, String sagaReplyChannel);
}
