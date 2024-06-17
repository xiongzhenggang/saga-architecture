package com.xzg.orchestrator.kit.command;

import com.xzg.orchestrator.kit.command.service.CommandProducer;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.message.producer.MessageProducer;

import java.util.Map;


/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandProducerImpl
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 3:03
 * @version: 1.0
 */
public class CommandProducerImpl implements CommandProducer {

    private final MessageProducer messageProducer;
    private final CommandNameMapping commandNameMapping;

    public CommandProducerImpl(MessageProducer messageProducer, CommandNameMapping commandNameMapping) {
        this.messageProducer = messageProducer;
        this.commandNameMapping = commandNameMapping;
    }

    @Override
    public Message send(String channel, Command command, String replyTo, Map<String, String> headers) {
        return send(channel, null, command, replyTo, headers);
    }

    @Override
    public Message sendNotification(String channel, Command command, Map<String, String> headers) {
        return send(channel, null, command, null, headers);
    }

    @Override
    public Message send(String channel, String resource, Command command, String replyTo, Map<String, String> headers) {
        Message message = CommandMessageFactory.makeMessage(commandNameMapping, channel, resource, command, replyTo, headers);
        messageProducer.send(channel, message);
        return message;
    }
}

    