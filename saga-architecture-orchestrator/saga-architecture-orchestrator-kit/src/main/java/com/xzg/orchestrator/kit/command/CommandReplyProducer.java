package com.xzg.orchestrator.kit.command;

import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.message.MessageBuilder;
import com.xzg.orchestrator.kit.message.producer.MessageProducer;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandReplyProducer
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 2:53
 * @version: 1.0
 */

@Service
public class CommandReplyProducer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private final MessageProducer messageProducer;

    public CommandReplyProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public List<Message> sendReplies(CommandReplyToken commandReplyToken, Message... replies) {
        return sendReplies(commandReplyToken, Arrays.asList(replies));
    }

    /**
     * 消费者收到消息处理后发送处理结果
     * @param commandReplyToken
     * @param replies
     * @return
     */
    public List<Message> sendReplies(CommandReplyToken commandReplyToken, List<Message> replies) {
        if (commandReplyToken.getReplyChannel() == null) {
            if (!replies.isEmpty()) {
                throw new RuntimeException("Replies to send but not replyTo channel");
            }
            return Collections.emptyList();
        }
        if (replies.isEmpty()) {
            logger.trace("Null replies - not publishing");
        }
        String replyChannel = commandReplyToken.getReplyChannel();
        List<Message> results = new ArrayList<>(replies.size());
        for (Message reply : replies) {
            Message message = MessageBuilder
                    .withMessage(reply)
                    .withExtraHeaders("", commandReplyToken.getReplyHeaders())
                    .build();
            messageProducer.send(replyChannel, message);
            results.add(message);
        }
        return results;
    }
}


    