package com.xzg.orchestrator.kit.message.consumer.kafka;

import com.xzg.orchestrator.kit.message.consumer.KeyValue;
import com.xzg.orchestrator.kit.message.consumer.MultiMessageEncoder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.List;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: EventuateKafkaMultiMessage
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:31
 * @version: 1.0
 */
public class EventuateKafkaMultiMessage extends KeyValue {

    private List<EventuateKafkaMultiMessageHeader> headers;

    public EventuateKafkaMultiMessage(String key, String value) {
        this(key, value, Collections.emptyList());
    }

    public EventuateKafkaMultiMessage(String key, String value, List<EventuateKafkaMultiMessageHeader> headers) {
        super(key, value);
        this.headers = headers;
    }


    public List<EventuateKafkaMultiMessageHeader> getHeaders() {
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public int estimateSize() {
        int headerSize = MultiMessageEncoder.MessagesEncoder.HeadersEncoder.HEADER_SIZE;
        int messagesSize = KeyValue.estimateSize(headers);
        return super.estimateSize() + headerSize + messagesSize;
    }


}


    