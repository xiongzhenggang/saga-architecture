package com.xzg.orchestrator.kit.dsl;


import com.xzg.orchestrator.kit.event.Message;

public interface ISagaStep<Data> {
    boolean isSuccessfulReply(boolean compensating, Message message);

    boolean hasAction(Data data);

    boolean hasCompensation(Data data);
}
