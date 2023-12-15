package com.xzg.orchestrator.kit.orchestration.saga.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiongzhenggang
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SerializedSagaData {
  private String sagaDataType;
  private String sagaDataJSON;

}
