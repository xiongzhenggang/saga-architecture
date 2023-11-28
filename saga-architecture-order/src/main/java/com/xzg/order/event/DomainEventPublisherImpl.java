package com.xzg.order.event;

import com.xzg.orchestrator.kit.event.DomainEvent;
import com.xzg.orchestrator.kit.event.EventUtil;
import com.xzg.orchestrator.kit.event.producer.MessageProducer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: org.xzg.order.event
 * @className: DomainEventPublisherImpl
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 4:11
 * @version: 1.0
 */
@Service
public class DomainEventPublisherImpl implements DomainEventPublisher {

    /**
     * 导入
     */
    @Resource
    private MessageProducer messageProducer;
    @Resource
    private DomainEventNameMapping domainEventNameMapping;

//    public DomainEventPublisherImpl(MessageProducer messageProducer, DomainEventNameMapping domainEventNameMapping) {
//        this.messageProducer = messageProducer;
//        this.domainEventNameMapping = domainEventNameMapping;
//    }

    @Override
    public void publish(String aggregateType, Object aggregateId, List<DomainEvent> domainEvents) {
        publish(aggregateType, aggregateId, Collections.emptyMap(), domainEvents);
    }

    @Override
    public void publish(String aggregateType,
                        Object aggregateId,
                        Map<String, String> headers,
                        List<DomainEvent> domainEvents) {

        for (DomainEvent event : domainEvents) {
            messageProducer.send(aggregateType,
                    EventUtil.makeMessageForDomainEvent(aggregateType, aggregateId, headers, event,
                            domainEventNameMapping.eventToExternalEventType(aggregateType, event)));

        }
    }
}
    