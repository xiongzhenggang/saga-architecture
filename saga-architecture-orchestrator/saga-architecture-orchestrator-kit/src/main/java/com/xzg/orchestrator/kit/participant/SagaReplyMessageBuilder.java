package com.xzg.orchestrator.kit.participant;

import com.xzg.library.config.infrastructure.utility.JsonUtil;
import com.xzg.orchestrator.kit.common.LockTarget;
import com.xzg.orchestrator.kit.command.Success;
import com.xzg.orchestrator.kit.orchestration.dsl.ReplyMessageHeaders;
import com.xzg.orchestrator.kit.orchestration.dsl.enums.CommandReplyOutcome;
import com.xzg.orchestrator.kit.event.Message;
import com.xzg.orchestrator.kit.event.MessageBuilder;

import java.util.Optional;

public class SagaReplyMessageBuilder extends MessageBuilder {

  private Optional<LockTarget> lockTarget = Optional.empty();

  public SagaReplyMessageBuilder(LockTarget lockTarget) {
    this.lockTarget = Optional.of(lockTarget);
  }

  public static SagaReplyMessageBuilder withLock(Class type, Object id) {
    return new SagaReplyMessageBuilder(new LockTarget(type, id));
  }

  private <T> Message with(T reply, CommandReplyOutcome outcome) {
    this.body = JsonUtil.object2JsonStr(reply);
    withHeader(ReplyMessageHeaders.REPLY_OUTCOME, outcome.name());
    withHeader(ReplyMessageHeaders.REPLY_TYPE, reply.getClass().getName());
    return new SagaReplyMessage(body, headers, lockTarget);
  }

  public Message withSuccess(Object reply) {
    return with(reply, CommandReplyOutcome.SUCCESS);
  }

  public Message withSuccess() {
    return withSuccess(new Success());
  }

}
