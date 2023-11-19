package com.xzg.order.event;

import com.xzg.orchestrator.kit.event.DomainEvent;

import java.util.List;
import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: org.xzg.order.event
 * @className: DomainEventPublisher
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 3:17
 * @version: 1.0
 */
public interface DomainEventPublisher {

    void publish(String aggregateType, Object aggregateId, List<DomainEvent> domainEvents);

    void publish(String aggregateType, Object aggregateId, Map<String, String> headers, List<DomainEvent> domainEvents);

    default void publish(Class<?> aggregateType, Object aggregateId, List<DomainEvent> domainEvents) {
        publish(aggregateType.getName(), aggregateId, domainEvents);
    }
}


    