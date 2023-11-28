package com.xzg.orchestrator.kit.orchestration.dsl;


import com.xzg.orchestrator.kit.event.Message;
import com.xzg.orchestrator.kit.orchestration.saga.SagaActions;
import com.xzg.orchestrator.kit.orchestration.saga.SagaDefinition;

import java.util.List;

import static java.util.function.Function.identity;

public class SimpleSagaDefinition<Data>
        extends AbstractSimpleSagaDefinition<Data, SagaStep<Data>, StepToExecute<Data>, SagaActionsProvider<Data>>
        implements SagaDefinition<Data> {


  public SimpleSagaDefinition(List<SagaStep<Data>> steps) {
    super(steps);
  }

  @Override
  public SagaActions<Data> start(Data sagaData) {
    return toSagaActions(firstStepToExecute(sagaData));
  }

  @Override
  public SagaActions<Data> handleReply(String sagaType, String sagaId, String currentState, Data sagaData, Message message) {

    SagaExecutionState state = SagaExecutionStateJsonSerde.decodeState(currentState);
    SagaStep<Data> currentStep = steps.get(state.getCurrentlyExecuting());
    boolean compensating = state.isCompensating();

    currentStep.getReplyHandler(message, compensating).ifPresent(handler -> invokeReplyHandler(message, sagaData, (d, m) -> {
      handler.accept(d, m);
      return null;
    }));

    SagaActionsProvider<Data> sap = sagaActionsForNextStep(sagaType, sagaId, sagaData, message, state, currentStep, compensating);
    return toSagaActions(sap);
  }

  private SagaActions<Data> toSagaActions(SagaActionsProvider<Data> sap) {
    return sap.toSagaActions(identity(), identity());
  }


  @Override
  protected SagaActionsProvider<Data> makeSagaActionsProvider(SagaActions<Data> sagaActions) {
    return new SagaActionsProvider<>(sagaActions);
  }

  @Override
  protected SagaActionsProvider<Data> makeSagaActionsProvider(StepToExecute<Data> stepToExecute, Data data, SagaExecutionState state) {
    return new SagaActionsProvider<>(() -> stepToExecute.executeStep(data, state));
  }


  @Override
  protected StepToExecute<Data> makeStepToExecute(int skipped, boolean compensating, SagaStep<Data> step) {
      return new StepToExecute<>(step, skipped, compensating);
  }



}
