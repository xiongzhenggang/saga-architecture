package org.xzg.order.config;

public class SagaStartedEvent extends SagaLifecycleEvent {

    public SagaStartedEvent(Object source, String sagaId) {
        super(source, sagaId);
    }


}
