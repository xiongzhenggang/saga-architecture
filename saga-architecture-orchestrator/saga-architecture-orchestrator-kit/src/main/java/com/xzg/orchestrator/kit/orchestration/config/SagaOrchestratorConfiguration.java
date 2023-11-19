//package com.xzg.orchestrator.kit.orchestration.config;
//
//import com.xzg.orchestrator.kit.common.SagaLockManager;
//import com.xzg.orchestrator.kit.common.config.EventuateTramSagaCommonConfiguration;
//import com.xzg.orchestrator.kit.orchestration.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
//import java.util.Collection;
//
//@Configuration
//@Import({TramCommandProducerConfiguration.class, EventuateTramSagaCommonConfiguration.class})
//public class SagaOrchestratorConfiguration {
//
//
//  @Bean
//  public SagaInstanceRepository sagaInstanceRepository(EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor,
//                                                       EventuateSchema eventuateSchema) {
//    return new SagaInstanceRepositoryJdbc(eventuateJdbcStatementExecutor, new ApplicationIdGenerator(), eventuateSchema);
//  }
//
//  @Bean
//  public SagaCommandProducer sagaCommandProducer(CommandProducer commandProducer) {
//    return new SagaCommandProducerImpl(commandProducer);
//  }
//
//  @Bean
//  public SagaInstanceFactory sagaInstanceFactory(SagaInstanceRepository sagaInstanceRepository, CommandProducer
//          commandProducer, MessageConsumer messageConsumer,
//                                                 SagaLockManager sagaLockManager, SagaCommandProducer sagaCommandProducer, Collection<Saga<?>> sagas) {
//    SagaManagerFactory smf = new SagaManagerFactory(sagaInstanceRepository, commandProducer, messageConsumer,
//            sagaLockManager, sagaCommandProducer);
//    return new SagaInstanceFactory(smf, sagas);
//  }
//}
