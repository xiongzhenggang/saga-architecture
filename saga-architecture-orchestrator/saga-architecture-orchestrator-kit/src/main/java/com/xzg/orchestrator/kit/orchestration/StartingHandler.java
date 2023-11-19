package com.xzg.orchestrator.kit.orchestration;

import com.xzg.orchestrator.kit.orchestration.saga.SagaActions;

import java.util.function.Function;

public interface StartingHandler<Data> extends Function<Data, SagaActions> {
}
