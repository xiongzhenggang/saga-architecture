package com.xzg.orchestrator.kit.orchestration.saga;

import com.xzg.orchestrator.kit.orchestration.saga.model.SagaInstance;

public class SagaInstanceData<Data> {

  private final SagaInstance sagaInstance;
  private final Data sagaData;

  public SagaInstanceData(SagaInstance sagaInstance, Data sagaData) {
    this.sagaInstance = sagaInstance;
    this.sagaData = sagaData;
  }

  public SagaInstance getSagaInstance() {
    return sagaInstance;
  }

  public Data getSagaData() {
    return sagaData;
  }
}
