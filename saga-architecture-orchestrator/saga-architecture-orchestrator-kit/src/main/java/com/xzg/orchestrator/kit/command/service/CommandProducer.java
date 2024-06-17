package com.xzg.orchestrator.kit.command.service;

import com.xzg.orchestrator.kit.command.Command;
import com.xzg.orchestrator.kit.message.Message;

import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandProducer
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 2:44
 * @version: 1.0
 */
public interface CommandProducer {

    /**
     * Sends a command
     * @param channel the channel to send the command to
     * @param command the command to send
     * @param replyTo the channel to send the reply to
     * @param headers additional headers
     * @return the id of the sent command
     */
    Message send(String channel, Command command, String replyTo,
                Map<String, String> headers);
    /**
     * Sends a command
     * @param channel the channel to send the command to
     * @param command the command to send
     * @param headers additional headers
     * @return the id of the sent command
     */
    Message sendNotification(String channel, Command command,
                            Map<String, String> headers);

    /**
     * Sends a command
     * @param channel the channel to send the command to
     * @param resource
     * @param command the command to send
     * @param replyTo the channel to send the reply to
     * @param headers additional headers
     * @return the id of the sent command
     */
    Message send(String channel, String resource, Command command, String replyTo, Map<String, String> headers);
}


    