package com.xzg.orchestrator.kit.orchestration.saga;


import com.xzg.orchestrator.kit.command.service.CommandProducer;
import com.xzg.orchestrator.kit.message.consumer.CommonMessageConsumer;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaInstanceRepository;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaMessageRepository;

/**
 * @author xiongzhenggang
 */
public class SagaManagerFactory {

  private final SagaInstanceRepository sagaInstanceRepository;
  private final SagaMessageRepository sagaMessageRepository;
  private final CommandProducer commandProducer;
  private final CommonMessageConsumer messageConsumer;
  private final SagaCommandProducer sagaCommandProducer;

  public SagaManagerFactory(SagaInstanceRepository sagaInstanceRepository,
                            SagaMessageRepository sagaMessageRepository,
                            CommandProducer commandProducer,
                            CommonMessageConsumer messageConsumer,
                            SagaCommandProducer sagaCommandProducer) {
    this.sagaInstanceRepository = sagaInstanceRepository;
    this.sagaMessageRepository = sagaMessageRepository;
    this.commandProducer = commandProducer;
    this.messageConsumer = messageConsumer;
    this.sagaCommandProducer = sagaCommandProducer;

  }

  public <SagaData> SagaManagerImpl<SagaData> make(Saga<SagaData> saga) {
    return new SagaManagerImpl<>(saga, sagaInstanceRepository,sagaMessageRepository, commandProducer, messageConsumer, sagaCommandProducer);
  }


}
