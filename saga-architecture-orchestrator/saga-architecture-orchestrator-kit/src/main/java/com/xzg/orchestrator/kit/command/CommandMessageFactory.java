package com.xzg.orchestrator.kit.command;

import com.xzg.library.config.infrastructure.utility.JsonUtil;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.message.MessageBuilder;

import java.util.Map;
import java.util.UUID;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandMessageFactory
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 3:05
 * @version: 1.0
 */
public class CommandMessageFactory {
    public static Message makeMessage(CommandNameMapping commandNameMapping, String channel, Command command, String replyTo, Map<String, String> headers) {
        return makeMessage(commandNameMapping, channel, null, command, replyTo, headers);
    }

    public static Message makeMessage(CommandNameMapping commandNameMapping, String channel, String resource, Command command, String replyTo, Map<String, String> headers) {
        MessageBuilder builder = MessageBuilder.withPayload(JsonUtil.object2JsonStr(command))
                .withExtraHeaders("", headers)
                .withHeader(Message.ID, UUID.randomUUID().toString())
                .withHeader(CommandMessageHeaders.DESTINATION, channel)
                .withHeader(CommandMessageHeaders.COMMAND_TYPE, commandNameMapping.commandToExternalCommandType(command))
                ;

        if (replyTo != null) {
            builder.withHeader(CommandMessageHeaders.REPLY_TO, replyTo);
        }

        if (resource != null) {
            builder.withHeader(CommandMessageHeaders.RESOURCE, resource);
        }

        return builder.build();
    }
}


    