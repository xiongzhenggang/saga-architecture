package com.xzg.orchestrator.kit.orchestration.saga;

import com.xzg.orchestrator.kit.orchestration.saga.model.SagaInstance;

import java.util.Optional;

public interface SagaManager<Data> {
  SagaInstance create(Data sagaData);

  // TODO or should the saga have a pseudo-step that locks the resource

  void subscribeToReplyChannel();

  SagaInstance create(Data sagaData, Optional<String> lockTarget);
  SagaInstance create(Data data, Class targetClass, Object targetId);
}
