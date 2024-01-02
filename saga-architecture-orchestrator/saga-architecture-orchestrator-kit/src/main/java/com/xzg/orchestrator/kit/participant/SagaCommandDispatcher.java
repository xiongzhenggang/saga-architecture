package com.xzg.orchestrator.kit.participant;


import com.xzg.orchestrator.kit.command.*;
import com.xzg.orchestrator.kit.command.business.SagaUnlockCommand;
import com.xzg.orchestrator.kit.common.*;
import com.xzg.orchestrator.kit.message.CommandMessage;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.message.MessageBuilder;
import com.xzg.orchestrator.kit.message.consumer.CommonMessageConsumer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xiongzhenggang
 */
@Slf4j
public class SagaCommandDispatcher extends CommandDispatcher {

  private SagaLockManager sagaLockManager;

  public SagaCommandDispatcher(String commandDispatcherId,
                               CommandHandlers target,
                               CommonMessageConsumer messageConsumer,
                               SagaLockManager sagaLockManager,
                               CommandNameMapping commandNameMapping, CommandReplyProducer commandReplyProducer) {
    super(commandDispatcherId, target, messageConsumer, commandNameMapping, commandReplyProducer);
    this.sagaLockManager = sagaLockManager;
  }

  @Override
  public void messageHandler(Message message) {
    log.info("receive message=:{}",message.getPayload());
    if (isUnlockMessage(message)) {
      String sagaType = getSagaType(message);
      String sagaId = getSagaId(message);
      String target = message.getRequiredHeader(CommandMessageHeaders.RESOURCE);
      sagaLockManager.unlock(sagaId, target).ifPresent(m -> super.messageHandler(message));
    } else {
      try {
        super.messageHandler(message);
      } catch (StashMessageRequiredException e) {
        String sagaType = getSagaType(message);
        String sagaId = getSagaId(message);
        String target = e.getTarget();
        sagaLockManager.stashMessage(sagaType, sagaId, target, message);
      }
    }
  }

  private String getSagaId(Message message) {
    return message.getRequiredHeader(SagaCommandHeaders.SAGA_ID);
  }

  private String getSagaType(Message message) {
    return message.getRequiredHeader(SagaCommandHeaders.SAGA_TYPE);
  }


  @Override
  protected List<Message> invoke(CommandHandler commandHandler, CommandMessage cm, Map<String, String> pathVars, CommandReplyToken commandReplyToken) {
    Optional<String> lockedTarget = Optional.empty();
    if (commandHandler instanceof SagaCommandHandler) {
      SagaCommandHandler sch = (SagaCommandHandler) commandHandler;
      if (sch.getPreLock().isPresent()) {
        LockTarget lockTarget = sch.getPreLock().get().apply(cm, new PathVariables(pathVars)); // TODO
        Message message = cm.getMessage();
        String sagaType = getSagaType(message);
        String sagaId = getSagaId(message);
        String target = lockTarget.getTarget();
        lockedTarget = Optional.of(target);
        if (!sagaLockManager.claimLock(sagaType, sagaId, target)) {
          throw new StashMessageRequiredException(target);
        }
      }
    }

    List<Message> messages = super.invoke(commandHandler, cm, pathVars, commandReplyToken);

    if (lockedTarget.isPresent()) {
      return addLockedHeader(messages, lockedTarget.get());
    } else {
      Optional<LockTarget> lt = getLock(messages);
      if (lt.isPresent()) {
        Message message = cm.getMessage();
        String sagaType = getSagaType(message);
        String sagaId = getSagaId(message);

        if (!sagaLockManager.claimLock(sagaType, sagaId, lt.get().getTarget())) {
          throw new RuntimeException("Cannot claim lock");
        }

        return addLockedHeader(messages, lt.get().getTarget());
      }
      else {
        return messages;
      }
    }
  }

  private Optional<LockTarget> getLock(List<Message> messages) {
    return messages.stream().filter(m -> m instanceof SagaReplyMessage && ((SagaReplyMessage) m).hasLockTarget()).findFirst().flatMap(m -> ((SagaReplyMessage)m).getLockTarget());
  }

  private List<Message> addLockedHeader(List<Message> messages, String lockedTarget) {
    // TODO - what about the isEmpty case??
    // TODO - sagas must return messages
    return messages.stream().map(m -> MessageBuilder.withMessage(m).withHeader(SagaReplyHeaders.REPLY_LOCKED, lockedTarget).build()).collect(Collectors.toList());
  }


  private boolean isUnlockMessage(Message message) {
    return message.getRequiredHeader(CommandMessageHeaders.COMMAND_TYPE).equals(SagaUnlockCommand.class.getName());
  }

}
