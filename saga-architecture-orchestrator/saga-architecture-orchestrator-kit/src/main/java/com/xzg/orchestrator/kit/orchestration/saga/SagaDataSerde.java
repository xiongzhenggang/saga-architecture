package com.xzg.orchestrator.kit.orchestration.saga;


import com.xzg.library.config.infrastructure.utility.JsonUtil;
import com.xzg.orchestrator.kit.orchestration.saga.model.SerializedSagaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SagaDataSerde {
  private static Logger logger = LoggerFactory.getLogger(SagaDataSerde.class);

  public static <Data> SerializedSagaData serializeSagaData(Data sagaData) {
    return new SerializedSagaData(sagaData.getClass().getName(), JsonUtil.object2JsonStr(sagaData));
  }

  public static <Data> Data deserializeSagaData(SerializedSagaData serializedSagaData) {
    Class<?> clasz = null;
    try {
      clasz = Thread.currentThread().getContextClassLoader().loadClass(serializedSagaData.getSagaDataType());
    } catch (ClassNotFoundException e) {
      logger.error("Class not found", e);
      throw new RuntimeException("Class not found", e);
    }
    Object x = JsonUtil.jsonStr2obj(serializedSagaData.getSagaDataJSON(), clasz);
    return (Data)x;
  }
}
