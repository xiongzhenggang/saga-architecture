package com.xzg.orchestrator.kit.participant;


import com.xzg.orchestrator.kit.command.*;
import com.xzg.orchestrator.kit.command.business.SagaUnlockCommand;
import com.xzg.orchestrator.kit.common.*;
import com.xzg.orchestrator.kit.message.CommandMessage;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.message.MessageBuilder;
import com.xzg.orchestrator.kit.message.consumer.CommonMessageConsumer;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaMessageRepository;
import com.xzg.orchestrator.kit.orchestration.saga.enums.SendStatusEnum;
import com.xzg.orchestrator.kit.orchestration.saga.enums.SourceEnum;
import com.xzg.orchestrator.kit.orchestration.saga.model.SagaMessage;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
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
//  private  TransactionTemplate transactionTemplate;
  private SagaMessageRepository sagaMessageRepository;
  public SagaCommandDispatcher(String commandDispatcherId,
                               CommandHandlers target,
                               CommonMessageConsumer messageConsumer,
                               SagaLockManager sagaLockManager,
                               CommandNameMapping commandNameMapping,
                               CommandReplyProducer commandReplyProducer,
                               SagaMessageRepository sagaMessageRepository) {
    super(commandDispatcherId, target, messageConsumer, commandNameMapping, commandReplyProducer);
    this.sagaLockManager = sagaLockManager;
    this.sagaMessageRepository = sagaMessageRepository;
  }

  @Override
  public void messageHandler(Message message) {
    log.info("receive message=:{}",message.getPayload());
    //保存
    saveSagaMessage(message);
    if (isUnlockMessage(message)) {
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

    /**
     * @param message
     */
    private void saveSagaMessage(Message message){
      String serial = message.getId();
      //@todo validate duplicate message by serial
      SagaMessage sagaMessage = new SagaMessage();
      message.getHeader(SagaCommandHeaders.SAGA_ID).ifPresent(sagaMessage::setSagaId);
      message.getHeader(CommandMessageHeaders.COMMAND_TYPE).ifPresent(sagaMessage::setType);
      sagaMessage.setHeaders(message.getHeaders().toString());
      sagaMessage.setPayload(message.getPayload());
      sagaMessage.setSerial(serial);
      sagaMessage.setSource(SourceEnum.RECEIVE.getValue());
      sagaMessage.setSendStatus(SendStatusEnum.SUCCESS.name());
      sagaMessage.setCreatedTime(LocalDateTime.now());
      sagaMessageRepository.save(sagaMessage);
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
