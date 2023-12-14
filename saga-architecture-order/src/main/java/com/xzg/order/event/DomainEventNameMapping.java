package com.xzg.order.event;

import com.xzg.orchestrator.kit.event.DomainEvent;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.order.event
 * @className: DomainEventNameMapping
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 4:13
 * @version: 1.0
 */
public interface DomainEventNameMapping {

    String eventToExternalEventType(String aggregateType, DomainEvent event);
    String externalEventTypeToEventClassName(String aggregateType, String eventTypeHeader);
}

    