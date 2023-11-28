package com.xzg.orchestrator.kit.orchestration;

import lombok.Data;

/**
 * @author xiongzhenggang
 */
@Data
public class SerializedSagaData {

  private String sagaDataType;
  private String sagaDataJSON;

  public SerializedSagaData(String sagaDataType, String sagaDataJSON) {
    this.sagaDataType = sagaDataType;
    this.sagaDataJSON = sagaDataJSON;
  }
}
