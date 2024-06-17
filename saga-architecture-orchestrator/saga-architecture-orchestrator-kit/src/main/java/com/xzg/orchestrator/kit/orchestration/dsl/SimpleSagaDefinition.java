package com.xzg.orchestrator.kit.orchestration.dsl;


import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.orchestration.saga.SagaActions;
import com.xzg.orchestrator.kit.orchestration.saga.SagaDefinition;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static java.util.function.Function.identity;
@Slf4j
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
    log.info("======> saga execute= step:{}",state.getCurrentlyExecuting());
    SagaStep<Data> currentStep = steps.get(state.getCurrentlyExecuting());
    boolean compensating = state.isCompensating();
    //补偿则执行补偿事务步骤，否则执行更改状态action
    currentStep.getReplyHandler(message, compensating)
            .ifPresent(handler -> invokeReplyHandler(message, sagaData, (d, m) -> {
              handler.accept(d, m);
      return Optional.empty();
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
