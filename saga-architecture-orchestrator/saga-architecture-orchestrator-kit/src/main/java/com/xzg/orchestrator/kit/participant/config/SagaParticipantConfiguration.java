//package com.xzg.orchestrator.kit.participant.config;
//
//import com.xzg.orchestrator.kit.common.SagaLockManager;
//import com.xzg.orchestrator.kit.common.config.EventuateTramSagaCommonConfiguration;
//import com.xzg.orchestrator.kit.participant.SagaCommandDispatcherFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
//@Configuration
//@Import({EventuateTramSagaCommonConfiguration.class})
//public class SagaParticipantConfiguration {
//  @Bean
//  public SagaCommandDispatcherFactory sagaCommandDispatcherFactory(MessageConsumer messageConsumer,
//                                                                   SagaLockManager sagaLockManager,
//                                                                   CommandNameMapping commandNameMapping,
//                                                                   CommandReplyProducer commandReplyProducer) {
//    return new SagaCommandDispatcherFactory(messageConsumer, sagaLockManager, commandNameMapping, commandReplyProducer);
//  }
//  @Bean
//  public CommandReplyProducer commandReplyProducer(MessageProducer messageProducer) {
//    return new CommandReplyProducer(messageProducer);
//  }
//
//
//}
