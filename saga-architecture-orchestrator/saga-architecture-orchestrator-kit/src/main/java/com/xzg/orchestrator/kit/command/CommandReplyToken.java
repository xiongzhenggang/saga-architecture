package com.xzg.orchestrator.kit.command;

import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandReplyToken
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 6:54
 * @version: 1.0
 */
public class CommandReplyToken {
    private Map<String, String> replyHeaders;
    private String replyChannel;

    public CommandReplyToken(Map<String, String> correlationHeaders, String replyChannel) {
        this.replyHeaders = correlationHeaders;
        this.replyChannel = replyChannel;
    }

    private CommandReplyToken() {
        // For ObjectMapper
    }

    public Map<String, String> getReplyHeaders() {
        return replyHeaders;
    }

    public String getReplyChannel() {
        return replyChannel;
    }

    public void setReplyHeaders(Map<String, String> replyHeaders) {
        this.replyHeaders = replyHeaders;
    }

    public void setReplyChannel(String replyChannel) {
        this.replyChannel = replyChannel;
    }
}

    