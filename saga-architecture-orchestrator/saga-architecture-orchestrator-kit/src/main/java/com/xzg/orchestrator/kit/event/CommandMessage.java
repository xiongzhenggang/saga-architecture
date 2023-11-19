package com.xzg.orchestrator.kit.event;

import lombok.Data;

import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event
 * @className: CommandMessage
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 5:40
 * @version: 1.0
 */
@Data
public class CommandMessage<T> {

    private String messageId;
    private T command;
    private Map<String, String> correlationHeaders;
    private Message message;

    public Message getMessage() {
        return message;
    }

    public CommandMessage(String messageId, T command, Map<String, String> correlationHeaders, Message message) {
        this.messageId = messageId;
        this.command = command;
        this.correlationHeaders = correlationHeaders;
        this.message = message;
    }


    public String getMessageId() {
        return messageId;
    }

    public T getCommand() {
        return command;
    }

    public Map<String, String> getCorrelationHeaders() {
        return correlationHeaders;
    }
}