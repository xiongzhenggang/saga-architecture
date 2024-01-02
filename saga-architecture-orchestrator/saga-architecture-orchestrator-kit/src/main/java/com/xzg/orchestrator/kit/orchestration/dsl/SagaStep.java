package com.xzg.orchestrator.kit.orchestration.dsl;


import com.xzg.orchestrator.kit.message.Message;

import java.util.Optional;
import java.util.function.BiConsumer;

public interface SagaStep<Data> extends ISagaStep<Data> {

  Optional<BiConsumer<Data, Object>> getReplyHandler(Message message, boolean compensating);

  StepOutcome makeStepOutcome(Data data, boolean compensating);

}
