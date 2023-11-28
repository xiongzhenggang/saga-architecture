package com.xzg.orchestrator.kit.orchestration.dsl;


import com.xzg.orchestrator.kit.orchestration.saga.SagaDefinition;

import java.util.LinkedList;
import java.util.List;

public class SimpleSagaDefinitionBuilder<Data> {

  private List<SagaStep<Data>> sagaSteps = new LinkedList<>();

  public void addStep(SagaStep<Data> sagaStep) {
    sagaSteps.add(sagaStep);
  }

  public SagaDefinition<Data> build() {
    return new SimpleSagaDefinition<>(sagaSteps);
  }
}
