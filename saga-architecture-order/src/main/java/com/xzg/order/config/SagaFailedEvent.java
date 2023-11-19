package com.xzg.order.config;

public class SagaFailedEvent extends SagaLifecycleEvent {

    public SagaFailedEvent(Object source, String sagaId) {
        super(source, sagaId);
    }


}
