package com.xzg.orchestrator.kit.dsl;


import com.xzg.orchestrator.kit.orchestration.saga.SagaActions;

import java.util.function.Supplier;

public class SagaActionsProvider<Data> extends AbstractSagaActionsProvider<Data, SagaActions<Data>> {

    public SagaActionsProvider(SagaActions<Data> sagaActions) {
        super(sagaActions);
    }

    public SagaActionsProvider(Supplier<SagaActions<Data>> sagaActionsSupport) {
        super(sagaActionsSupport);
    }

}
