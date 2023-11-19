package com.xzg.orchestrator.kit.orchestration.saga;


import com.xzg.orchestrator.kit.event.DomainEvent;

public class SagaCompletedForAggregateEvent implements DomainEvent {
  public SagaCompletedForAggregateEvent(String sagaId) {
  }
}
