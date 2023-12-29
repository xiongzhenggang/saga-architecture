package com.xzg.orchestrator.kit.orchestration.saga.dao;

import com.xzg.orchestrator.kit.orchestration.saga.model.SagaInstance;

public interface SagaInstanceRepository {

  void save(SagaInstance sagaInstance);
  SagaInstance find(String sagaType, String sagaId);
  void update(SagaInstance sagaInstance);

}
