package com.xzg.orchestrator.kit.orchestration.saga;

import com.xzg.orchestrator.kit.orchestration.saga.SagaActions;

public interface SagaStateMachineAction<Data, Reply> {

  SagaActions<Data> apply(Data data, Reply reply);


}
