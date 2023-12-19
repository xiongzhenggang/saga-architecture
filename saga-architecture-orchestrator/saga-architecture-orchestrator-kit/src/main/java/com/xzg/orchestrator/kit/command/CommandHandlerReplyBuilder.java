package com.xzg.orchestrator.kit.command;

import com.xzg.library.config.infrastructure.utility.JsonUtil;
import com.xzg.orchestrator.kit.orchestration.dsl.ReplyMessageHeaders;
import com.xzg.orchestrator.kit.orchestration.dsl.enums.CommandReplyOutcome;
import com.xzg.orchestrator.kit.event.Message;
import com.xzg.orchestrator.kit.event.MessageBuilder;

import java.util.UUID;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandHandlerReplyBuilder
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 12:32
 * @version: 1.0
 */
public class CommandHandlerReplyBuilder {


    private static <T> Message with(T reply, CommandReplyOutcome outcome) {
        MessageBuilder messageBuilder = MessageBuilder
                .withPayload(JsonUtil.object2JsonStr(reply))
                .withHeader(Message.ID, UUID.randomUUID().toString())
                .withHeader(ReplyMessageHeaders.REPLY_OUTCOME, outcome.name())
                .withHeader(ReplyMessageHeaders.REPLY_TYPE, reply.getClass().getName());
        return messageBuilder.build();
    }

    public static Message withSuccess(Object reply) {
        return with(reply, CommandReplyOutcome.SUCCESS);
    }

    public static Message withSuccess() {
        return withSuccess(new Success());
    }

    public static Message withFailure() {
        return withFailure(new Failure());
    }
    public static Message withFailure(Object reply) {
        return with(reply, CommandReplyOutcome.FAILURE);
    }

}

    