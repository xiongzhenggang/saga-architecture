package com.xzg.orchestrator.kit.event;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event
 * @className: MessageImpl
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 6:59
 * @version: 1.0
 */
@ToString
public class MessageImpl implements Message {

    private String payload;
    private Map<String, String> headers;

    public MessageImpl() {
    }

    public MessageImpl(String payload, Map<String, String> headers) {
        this.payload = payload;
        this.headers = headers;
    }


    public String getPayload() {
        return payload;
    }

    @Override
    public Optional<String> getHeader(String name) {
        return Optional.ofNullable(headers.get(name));
    }

    @Override
    public String getRequiredHeader(String name) {
        String s = headers.get(name);
        if (s == null)
            throw new RuntimeException("No such header: " + name + " in this message " + this);
        else
            return s;
    }

    @Override
    public boolean hasHeader(String name) {
        return headers.containsKey(name);
    }

    @Override
    public String getId() {
        return getRequiredHeader(Message.ID);
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }


    @Override
    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public void setHeader(String name, String value) {
        if (headers == null)
            headers = new HashMap<>();
        headers.put(name, value);
    }

    @Override
    public void removeHeader(String key) {
        headers.remove(key);
    }
}


    