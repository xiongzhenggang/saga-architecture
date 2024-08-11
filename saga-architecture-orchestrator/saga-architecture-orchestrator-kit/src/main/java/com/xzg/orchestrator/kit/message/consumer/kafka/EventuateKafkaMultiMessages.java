package com.xzg.orchestrator.kit.message.consumer.kafka;

import com.xzg.orchestrator.kit.message.consumer.KeyValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: EventuateKafkaMultiMessages
 * @author: xzg
 * @description: TODO
 * @date: 24/11/2023-下午 7:45
 * @version: 1.0
 */
public class EventuateKafkaMultiMessages {
    private List<EventuateKafkaMultiMessagesHeader> headers;
    private List<EventuateKafkaMultiMessage> messages;

    public EventuateKafkaMultiMessages(List<EventuateKafkaMultiMessage> messages) {
        this(Collections.emptyList(), messages);
    }

    public EventuateKafkaMultiMessages(List<EventuateKafkaMultiMessagesHeader> headers, List<EventuateKafkaMultiMessage> messages) {
        this.headers = headers;
        this.messages = messages;
    }

    public List<EventuateKafkaMultiMessagesHeader> getHeaders() {
        return headers;
    }

    public List<EventuateKafkaMultiMessage> getMessages() {
        return messages;
    }

    public int estimateSize() {
        return KeyValue.estimateSize(headers) + KeyValue.estimateSize(messages);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers, messages);
    }
}

    