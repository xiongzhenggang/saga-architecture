package com.xzg.orchestrator.kit.orchestration.saga;


import com.xzg.orchestrator.kit.message.Message;

public interface SagaDefinition<Data> {

  SagaActions<Data> start(Data sagaData);

  SagaActions<Data> handleReply(String sagaType, String sagaId, String currentState, Data sagaData, Message message);

}
