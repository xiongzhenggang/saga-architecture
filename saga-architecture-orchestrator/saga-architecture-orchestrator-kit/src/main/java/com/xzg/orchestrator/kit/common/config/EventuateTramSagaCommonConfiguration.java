//package com.xzg.orchestrator.kit.common.config;
//
//import com.xzg.orchestrator.kit.common.SagaLockManager;
//import com.xzg.orchestrator.kit.common.SagaLockManagerImpl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
//@Configuration
//@Import(EventuateCommonJdbcOperationsConfiguration.class)
//public class EventuateTramSagaCommonConfiguration {
//
//  @Bean
//  public SagaLockManager sagaLockManager(EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor,
//                                         EventuateSchema eventuateSchema) {
//    return new SagaLockManagerImpl(eventuateJdbcStatementExecutor, eventuateSchema);
//  }
//}