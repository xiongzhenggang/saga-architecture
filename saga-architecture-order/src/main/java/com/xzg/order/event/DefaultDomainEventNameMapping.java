package com.xzg.order.event;

import com.xzg.orchestrator.kit.event.DomainEvent;
import org.springframework.stereotype.Service;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.order.event
 * @className: DefaultDomainEventNameMapping
 * @author: xzg
 * @description: TODO
 * @date: 30/11/2023-下午 8:32
 * @version: 1.0
 */
@Service
public class DefaultDomainEventNameMapping implements DomainEventNameMapping {

    @Override
    public String eventToExternalEventType(String aggregateType, DomainEvent event) {
        return event.getClass().getName();
    }

    @Override
    public String externalEventTypeToEventClassName(String aggregateType, String eventTypeHeader) {
        return eventTypeHeader;
    }

}
    