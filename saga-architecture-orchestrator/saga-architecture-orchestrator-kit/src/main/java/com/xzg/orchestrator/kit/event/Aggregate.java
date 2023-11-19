package com.xzg.orchestrator.kit.event;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event
 * @className: Aggregate
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 12:50
 * @version: 1.0
 */
public interface Aggregate<T extends Aggregate> {

    /**
     * Update the aggregate
     * @param event the event representing the state change
     * @return the updated aggregate, which might be this
     */
    T applyEvent(Event event);
}
    