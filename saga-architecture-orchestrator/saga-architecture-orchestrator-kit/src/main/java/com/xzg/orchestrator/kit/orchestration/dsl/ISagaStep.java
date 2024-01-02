package com.xzg.orchestrator.kit.orchestration.dsl;


import com.xzg.orchestrator.kit.message.Message;

public interface ISagaStep<Data> {
    boolean isSuccessfulReply(boolean compensating, Message message);

    boolean hasAction(Data data);

    boolean hasCompensation(Data data);
}
