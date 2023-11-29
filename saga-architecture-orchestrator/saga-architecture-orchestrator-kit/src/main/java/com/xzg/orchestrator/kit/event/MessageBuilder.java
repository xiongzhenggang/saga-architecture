package com.xzg.orchestrator.kit.event;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event
 * @className: MessageBuilder
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 6:59
 * @version: 1.0
 */
public class MessageBuilder {

    protected String body;
    protected Map<String, String> headers = new HashMap<>();

    protected MessageBuilder() {
    }

    public MessageBuilder(String body) {
        this.body = body;
    }

    public MessageBuilder(Message message) {
        this(message.getPayload());
        this.headers = message.getHeaders();
    }

    public static MessageBuilder withPayload(String payload) {
        return new MessageBuilder(payload);
    }

    public MessageBuilder withHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    public MessageBuilder withExtraHeaders(String prefix, Map<String, String> headers) {

        for (Map.Entry<String,String> entry : headers.entrySet()) {
            this.headers.put(prefix + entry.getKey(), entry.getValue());
        }

        return this;
    }

    public Message build() {
        return new MessageImpl(body, headers);
    }

    public static MessageBuilder withMessage(Message message) {
        return new MessageBuilder(message);
    }
}


    