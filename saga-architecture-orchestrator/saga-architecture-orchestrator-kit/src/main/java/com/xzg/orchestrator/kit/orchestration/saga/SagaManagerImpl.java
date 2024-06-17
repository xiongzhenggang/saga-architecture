package com.xzg.orchestrator.kit.orchestration.saga;

import com.xzg.orchestrator.kit.command.CommandMessageHeaders;
import com.xzg.orchestrator.kit.command.Failure;
import com.xzg.orchestrator.kit.command.Success;
import com.xzg.orchestrator.kit.command.business.SagaUnlockCommand;
import com.xzg.orchestrator.kit.command.service.CommandProducer;
import com.xzg.orchestrator.kit.common.LockTarget;
import com.xzg.orchestrator.kit.common.SagaCommandHeaders;
import com.xzg.orchestrator.kit.common.SagaReplyHeaders;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.message.MessageBuilder;
import com.xzg.orchestrator.kit.message.consumer.CommonMessageConsumer;
import com.xzg.orchestrator.kit.orchestration.dsl.ReplyMessageHeaders;
import com.xzg.orchestrator.kit.orchestration.dsl.enums.CommandReplyOutcome;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaInstanceRepository;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaMessageRepository;
import com.xzg.orchestrator.kit.orchestration.saga.model.DestinationAndResource;
import com.xzg.orchestrator.kit.orchestration.saga.model.SagaInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.util.Collections.singleton;

/**
 *
 * @param <Data>
 */
public class SagaManagerImpl<Data>
        implements SagaManager<Data> {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private Saga<Data> saga;
  private SagaInstanceRepository sagaInstanceRepository;
  private SagaMessageRepository sagaMessageRepository;
  private CommandProducer commandProducer;
  private CommonMessageConsumer messageConsumer;
  private SagaCommandProducer sagaCommandProducer;

  public SagaManagerImpl(Saga<Data> saga,
                         SagaInstanceRepository sagaInstanceRepository,
                         SagaMessageRepository sagaMessageRepository,
                         CommandProducer commandProducer,
                         CommonMessageConsumer messageConsumer,
                         SagaCommandProducer sagaCommandProducer) {
    this.saga = saga;
    this.sagaInstanceRepository = sagaInstanceRepository;
    this.sagaMessageRepository = sagaMessageRepository;
    this.commandProducer = commandProducer;
    this.messageConsumer = messageConsumer;
    this.sagaCommandProducer = sagaCommandProducer;
  }

  public void setSagaCommandProducer(SagaCommandProducer sagaCommandProducer) {
    this.sagaCommandProducer = sagaCommandProducer;
  }

  public void setSagaInstanceRepository(SagaInstanceRepository sagaInstanceRepository) {
    this.sagaInstanceRepository = sagaInstanceRepository;
  }



  public void setCommandProducer(CommandProducer commandProducer) {
    this.commandProducer = commandProducer;
  }

  public void setCommonMessageConsumer(CommonMessageConsumer messageConsumer) {
    this.messageConsumer = messageConsumer;
  }


  @Override
  public SagaInstance create(Data sagaData) {
    return create(sagaData, Optional.empty());
  }

  @Override
  public SagaInstance create(Data data, Class targetClass, Object targetId) {
    return create(data, Optional.of(new LockTarget(targetClass, targetId).getTarget()));
  }

  @Override
  public SagaInstance create(Data sagaData, Optional<String> resource) {
    SagaInstance sagaInstance = new SagaInstance();
    sagaInstance.setId(UUID.randomUUID().toString());
    sagaInstance.setSagaName(getSagaName());
    sagaInstance.setSagaType(getSagaType());
    sagaInstance.setSerializedSagaData( SagaDataSerde.serializeSagaData(sagaData));
    sagaInstance.setDestinationsAndResources(new ArrayList<>());
    sagaInstanceRepository.save(sagaInstance);
    String sagaId = sagaInstance.getId();
    logger.info("new sagaId ={}",sagaId);
    saga.onStarting(sagaId, sagaData);
    SagaActions<Data> actions = getStateDefinition().start(sagaData);
    actions.getLocalException().ifPresent(e -> {
      throw e;
    });
    processActions(saga.getSagaType(), sagaId, sagaInstance, sagaData, actions);
    return sagaInstance;
  }

  /**
   * 执行结束状态操作
   * @param sagaId
   * @param sagaInstance
   * @param compensating
   * @param failed
   * @param sagaData
   */
  private void performEndStateActions(String sagaId, SagaInstance sagaInstance, boolean compensating, boolean failed, Data sagaData) {
    for (DestinationAndResource dr : sagaInstance.getDestinationsAndResources()) {
      Map<String, String> headers = new HashMap<>();
      headers.put(SagaCommandHeaders.SAGA_ID, sagaId);
      headers.put(SagaCommandHeaders.SAGA_TYPE, getSagaType());
      commandProducer.send(dr.getDestination(), dr.getResource(), new SagaUnlockCommand(), makeSagaReplyChannel(), headers);
    }
    if (failed) {
      saga.onSagaFailed(sagaId, sagaData);
    }
    if (compensating) {
      saga.onSagaRolledBack(sagaId, sagaData);
    } else {
      saga.onSagaCompletedSuccessfully(sagaId, sagaData);
    }
  }

  private SagaDefinition<Data> getStateDefinition() {
    SagaDefinition<Data> sm = saga.getSagaDefinition();

    if (sm == null) {
      throw new RuntimeException("state machine cannot be null");
    }

    return sm;
  }

  private String getSagaType() {
    return saga.getSagaType();
  }
  private String getSagaName() {
    return saga.getClass().getName();
  }
  @Override
  public void subscribeToReplyChannel() {
    messageConsumer.subscribe(saga.getSagaType() + "-consumer", singleton(makeSagaReplyChannel()),
            this::handleMessage);
  }

  private String makeSagaReplyChannel() {
    return getSagaType() + "-reply";
  }


  public void handleMessage(Message message) {
    logger.debug("handle message invoked {}", message);
    if (message.hasHeader(SagaReplyHeaders.REPLY_SAGA_ID)) {
      handleReply(message);
    } else {
      logger.warn("Handle message doesn't know what to do with: {} ", message);
    }
  }


  private void handleReply(Message message) {
    if (!isReplyForThisSagaType(message)) {
      return;
    }
    logger.debug("Handle reply: {}", message);
    String sagaId = message.getRequiredHeader(SagaReplyHeaders.REPLY_SAGA_ID);
    String sagaType = message.getRequiredHeader(SagaReplyHeaders.REPLY_SAGA_TYPE);
    SagaInstance sagaInstance = sagaInstanceRepository.find(sagaType, sagaId);
    Data sagaData = SagaDataSerde.deserializeSagaData(sagaInstance.getSerializedSagaData());
    message.getHeader(SagaReplyHeaders.REPLY_LOCKED).ifPresent(lockedTarget -> {
      String destination = message.getRequiredHeader(CommandMessageHeaders.inReply(CommandMessageHeaders.DESTINATION));
      sagaInstance.addDestinationsAndResources(singleton(new DestinationAndResource(destination, lockedTarget)));
    });

    String currentState = sagaInstance.getStateName();

    logger.info("Current state={}", currentState);
    //执行回复
    SagaActions<Data> actions = getStateDefinition().handleReply(sagaType, sagaId, currentState, sagaData, message);

    logger.info("Handled reply. Sending commands {}", actions.getCommands());
    processActions(sagaType, sagaId, sagaInstance, sagaData, actions);
  }

  private void processActions(String sagaType, String sagaId, SagaInstance sagaInstance, Data sagaData, SagaActions<Data> actions) {
    while (true) {
      //本地失败的情况，通知其他
      if (actions.getLocalException().isPresent()) {
          actions = getStateDefinition().handleReply(sagaType, sagaId, actions.getUpdatedState().get(), actions.getUpdatedSagaData().get(), MessageBuilder
                  .withPayload("{}")
                  .withHeader(ReplyMessageHeaders.REPLY_OUTCOME, CommandReplyOutcome.FAILURE.name())
                  .withHeader(ReplyMessageHeaders.REPLY_TYPE, Failure.class.getName())
                  .build());
      } else {
        // only do this if successful 事务消息保存SagaMessage
        String lastRequestId = sagaCommandProducer.sendCommands(this.getSagaType(), sagaId, actions.getCommands(), this.makeSagaReplyChannel(),sagaMessageRepository);
        sagaInstance.setLastRequestId(lastRequestId);
        updateState(sagaInstance, actions);
        sagaInstance.setSerializedSagaData(SagaDataSerde.serializeSagaData(actions.getUpdatedSagaData().orElse(sagaData)));
        if (actions.isEndState()) {
          performEndStateActions(sagaId, sagaInstance, actions.isCompensating(), actions.isFailed(), sagaData);
        }
        sagaInstanceRepository.update(sagaInstance);
        if (actions.isReplyExpected()) {
          logger.info("======> 正常回复跳出：{}",actions.getClass());
          break;
        } else {
          actions = simulateSuccessfulReplyToLocalActionOrNotification(sagaType, sagaId, actions);
        }
      }
    }
  }

  /**
   * 本地action执行后模拟消息回复
   * @param sagaType
   * @param sagaId
   * @param actions
   * @return
   */
  private SagaActions<Data> simulateSuccessfulReplyToLocalActionOrNotification(String sagaType, String sagaId, SagaActions<Data> actions) {
    return getStateDefinition()
            .handleReply(sagaType, sagaId, actions.getUpdatedState().get(), actions.getUpdatedSagaData().get(),
              MessageBuilder
                .withPayload("{}")
                .withHeader(ReplyMessageHeaders.REPLY_OUTCOME, CommandReplyOutcome.SUCCESS.name())
                .withHeader(ReplyMessageHeaders.REPLY_TYPE, Success.class.getName())
                .build()
            );
  }

  private void updateState(SagaInstance sagaInstance, SagaActions<Data> actions) {
    actions.getUpdatedState().ifPresent(stateName -> {
      sagaInstance.setStateName(stateName);
      sagaInstance.setEndState(actions.isEndState());
      sagaInstance.setCompensating(actions.isCompensating());
      sagaInstance.setFailed(actions.isFailed());
    });
  }


  private Boolean isReplyForThisSagaType(Message message) {
    return message.getHeader(SagaReplyHeaders.REPLY_SAGA_TYPE).map(x -> x.equals(getSagaType())).orElse(false);
  }
}
