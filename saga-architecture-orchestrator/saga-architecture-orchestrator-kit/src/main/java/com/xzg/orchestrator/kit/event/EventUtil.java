package com.xzg.orchestrator.kit.event;

import com.xzg.library.config.infrastructure.utility.JsonUtil;
import com.xzg.orchestrator.kit.event.constant.EventMessageHeaders;

import java.util.Map;
import java.util.UUID;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event
 * @className: EventUtil
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 4:11
 * @version: 1.0
 */
public class EventUtil {

    public static Message makeMessageForDomainEvent(String aggregateType,
                                                    Object aggregateId,
                                                    Map<String, String> headers,
                                                    DomainEvent event,
                                                    String eventType) {
        String aggregateIdAsString = aggregateId.toString();
        return MessageBuilder
                .withPayload(JsonUtil.object2JsonStr(event))
                .withExtraHeaders("", headers)
                .withHeader(Message.ID, UUID.randomUUID().toString())
                .withHeader(Message.PARTITION_ID, aggregateIdAsString)
                .withHeader(EventMessageHeaders.AGGREGATE_ID, aggregateIdAsString)
                .withHeader(EventMessageHeaders.AGGREGATE_TYPE, aggregateType)
                .withHeader(EventMessageHeaders.EVENT_TYPE, eventType)
                .build();
    }
}

    