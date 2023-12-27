package com.xzg.orchestrator.kit.orchestration.saga;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiongzhenggang
 */
public interface Saga<Data> {
  Logger logger = LoggerFactory.getLogger(Saga.class);
  SagaDefinition<Data> getSagaDefinition();

  default String getSagaType() {
    return getClass().getName().replace("$", "_DLR_");
  }

  default void onStarting(String sagaId, Data data) {
    logger.info("onStarting开始sagaId=:{},sagaData={}",sagaId,data);
  }
  default void onSagaCompletedSuccessfully(String sagaId, Data data) {
    logger.info("onSagaCompletedSuccessfully 成功处理sagaId=:{},sagaData={}",sagaId,data);
  }
  default void onSagaRolledBack(String sagaId, Data data) {
    logger.info("onSagaRolledBack失败回滚 sagaId=:{},sagaData={}",sagaId,data);
  }
  default void onSagaFailed(String sagaId, Data data) {
    logger.info("onSagaFailed失败sagaId=:{},sagaData={}",sagaId,data);
  };
}
