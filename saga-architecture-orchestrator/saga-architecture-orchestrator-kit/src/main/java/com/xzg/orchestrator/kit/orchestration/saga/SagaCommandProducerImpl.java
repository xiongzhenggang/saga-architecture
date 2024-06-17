package com.xzg.orchestrator.kit.orchestration.saga;


import com.xzg.orchestrator.kit.command.CommandWithDestination;
import com.xzg.orchestrator.kit.command.service.CommandProducer;
import com.xzg.orchestrator.kit.common.SagaCommandHeaders;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.orchestration.CommandWithDestinationAndType;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaMessageRepository;
import com.xzg.orchestrator.kit.orchestration.saga.model.SagaMessage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
      //本地事务消息保存
      SagaMessage sagaMessage = saveSagaMessage(sagaMessageRepository,sagaType,sagaId,message);
      if (cwdt.isNotification()) {
        message = commandProducer.sendNotification(command.getDestinationChannel(), command.getCommand(), headers);
      } else {
        message = commandProducer.send(command.getDestinationChannel(), command.getResource(), command.getCommand(), sagaReplyChannel, headers);
      }
      updateSagaMessage(sagaMessage,sagaMessageRepository);
    }
    return message.getId();
  }
  private SagaMessage saveSagaMessage(SagaMessageRepository sagaMessageRepository,
                               String sagaType,
                               String sagaId,
                               Message message){
    SagaMessage sagaMessage = new SagaMessage();
    sagaMessage.setHeaders(message.getHeaders().toString());
    sagaMessage.setPayload(message.getPayload());
    sagaMessage.setType(sagaType);
    sagaMessage.setSerial(sagaId);
    sagaMessage.setSendStatus("SENDING");
    sagaMessage.setCreatedTime(LocalDateTime.now());
    return sagaMessageRepository.save(sagaMessage);
  }

  private void updateSagaMessage(SagaMessage sagaMessage,SagaMessageRepository sagaMessageRepository){
    sagaMessage.setSendStatus("SENT");
    sagaMessage.setUpdatedTime(LocalDateTime.now());
    sagaMessageRepository.save(sagaMessage);
  }
}
