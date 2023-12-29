package com.xzg.orchestrator.kit.participant;


import com.xzg.orchestrator.kit.command.CommandHandlers;
import com.xzg.orchestrator.kit.command.CommandNameMapping;
import com.xzg.orchestrator.kit.command.CommandReplyProducer;
import com.xzg.orchestrator.kit.common.SagaLockManager;
import com.xzg.orchestrator.kit.event.consumer.CommonMessageConsumer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SagaCommandDispatcherFactory {

  @Resource
  private CommonMessageConsumer kafkaMessageConsumer;
  @Resource
  private  SagaLockManager sagaLockManager;
  @Resource
  private  CommandNameMapping commandNameMapping;
  @Resource
  private  CommandReplyProducer commandReplyProducer;

  public SagaCommandDispatcher make(String commandDispatcherId, CommandHandlers target) {
    SagaCommandDispatcher sagaCommandDispatcher = new SagaCommandDispatcher(commandDispatcherId, target, kafkaMessageConsumer, sagaLockManager, commandNameMapping, commandReplyProducer);
    sagaCommandDispatcher.initialize();
    return sagaCommandDispatcher;
  }
}
