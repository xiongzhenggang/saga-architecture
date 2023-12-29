package com.xzg.orchestrator.kit.orchestration.saga;


import com.xzg.orchestrator.kit.command.service.CommandProducer;
import com.xzg.orchestrator.kit.event.consumer.CommonMessageConsumer;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaInstanceRepository;

/**
 * @author xiongzhenggang
 */
public class SagaManagerFactory {

  private final SagaInstanceRepository sagaInstanceRepository;
  private final CommandProducer commandProducer;
  private final CommonMessageConsumer messageConsumer;
  private final SagaCommandProducer sagaCommandProducer;

  public SagaManagerFactory(SagaInstanceRepository sagaInstanceRepository, CommandProducer
          commandProducer, CommonMessageConsumer messageConsumer, SagaCommandProducer sagaCommandProducer) {
    this.sagaInstanceRepository = sagaInstanceRepository;
    this.commandProducer = commandProducer;
    this.messageConsumer = messageConsumer;
    this.sagaCommandProducer = sagaCommandProducer;
  }

  public <SagaData> SagaManagerImpl<SagaData> make(Saga<SagaData> saga) {
    return new SagaManagerImpl<>(saga, sagaInstanceRepository, commandProducer, messageConsumer, sagaCommandProducer);
  }


}
