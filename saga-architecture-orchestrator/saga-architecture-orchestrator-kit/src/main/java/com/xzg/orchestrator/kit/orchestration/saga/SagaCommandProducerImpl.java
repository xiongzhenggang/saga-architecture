package com.xzg.orchestrator.kit.orchestration.saga;


import com.xzg.orchestrator.kit.command.CommandMessageHeaders;
import com.xzg.orchestrator.kit.command.CommandWithDestination;
import com.xzg.orchestrator.kit.command.service.CommandProducer;
import com.xzg.orchestrator.kit.common.SagaCommandHeaders;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.orchestration.CommandWithDestinationAndType;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaMessageRepository;
import com.xzg.orchestrator.kit.orchestration.saga.enums.SendStatusEnum;
import com.xzg.orchestrator.kit.orchestration.saga.enums.SourceEnum;
import com.xzg.orchestrator.kit.orchestration.saga.model.SagaMessage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SagaCommandProducerImpl implements SagaCommandProducer {

  private CommandProducer commandProducer;

  public SagaCommandProducerImpl(CommandProducer commandProducer) {
    this.commandProducer = commandProducer;
  }

  @Override
  public String sendCommands(String sagaType,
                             String sagaId,
                             List<CommandWithDestinationAndType> commands,
                             String sagaReplyChannel,
                             SagaMessageRepository sagaMessageRepository) {
    Message message = null;
    for (CommandWithDestinationAndType cwdt : commands) {
      CommandWithDestination command = cwdt.getCommandWithDestination();
      Map<String, String> headers = new HashMap<>(command.getExtraHeaders());
      headers.put(SagaCommandHeaders.SAGA_TYPE, sagaType);
      headers.put(SagaCommandHeaders.SAGA_ID, sagaId);
      if (cwdt.isNotification()) {
        message = commandProducer.sendNotification(command.getDestinationChannel(), command.getCommand(), headers);
      } else {
        message = commandProducer.send(command.getDestinationChannel(), command.getResource(), command.getCommand(), sagaReplyChannel, headers);
      }
      //本地事务消息保存
      saveSagaMessage(sagaMessageRepository,sagaId,message);
    }
    return Objects.isNull(message)?null:message.getId();
  }

  /**
   * @param sagaMessageRepository
   * @param sagaId
   * @param message
   */
  private void saveSagaMessage(SagaMessageRepository sagaMessageRepository,
                               String sagaId,
                               Message message){
    SagaMessage sagaMessage = new SagaMessage();
    sagaMessage.setSagaId(sagaId);
    sagaMessage.setHeaders(message.getHeaders().toString());
    sagaMessage.setPayload(message.getPayload());
    message.getHeader(SagaCommandHeaders.SAGA_ID).ifPresent(sagaMessage::setSagaId);
    message.getHeader(CommandMessageHeaders.COMMAND_TYPE).ifPresent(sagaMessage::setType);
    sagaMessage.setSerial(message.getId());
    sagaMessage.setSource(SourceEnum.SEND.getValue());
    sagaMessage.setSendStatus(SendStatusEnum.SUCCESS.name());
    sagaMessage.setCreatedTime(LocalDateTime.now());
    sagaMessageRepository.save(sagaMessage);
  }

}
