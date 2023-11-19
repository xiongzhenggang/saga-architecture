package com.xzg.orchestrator.kit.participant;


import com.xzg.orchestrator.kit.command.CommandHandlers;
import com.xzg.orchestrator.kit.command.CommandNameMapping;
import com.xzg.orchestrator.kit.command.CommandReplyProducer;
import com.xzg.orchestrator.kit.common.SagaLockManager;
import com.xzg.orchestrator.kit.event.consumer.MessageConsumer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SagaCommandDispatcherFactory {

  @Resource
  private  MessageConsumer messageConsumer;
  @Resource
  private  SagaLockManager sagaLockManager;
  @Resource
  private  CommandNameMapping commandNameMapping;
  @Resource
  private  CommandReplyProducer commandReplyProducer;

//  public SagaCommandDispatcherFactory(MessageConsumer messageConsumer,
//                                      SagaLockManager sagaLockManager,
//                                      CommandNameMapping commandNameMapping,
//                                      CommandReplyProducer commandReplyProducer) {
//    this.messageConsumer = messageConsumer;
//    this.sagaLockManager = sagaLockManager;
//    this.commandNameMapping = commandNameMapping;
//    this.commandReplyProducer = commandReplyProducer;
//  }

  public SagaCommandDispatcher make(String commandDispatcherId, CommandHandlers target) {
    SagaCommandDispatcher sagaCommandDispatcher = new SagaCommandDispatcher(commandDispatcherId, target, messageConsumer, sagaLockManager, commandNameMapping, commandReplyProducer);
    sagaCommandDispatcher.initialize();
    return sagaCommandDispatcher;
  }
}
