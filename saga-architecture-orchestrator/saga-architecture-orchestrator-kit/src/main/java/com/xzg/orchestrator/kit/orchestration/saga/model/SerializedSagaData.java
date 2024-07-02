package com.xzg.orchestrator.kit.orchestration.saga.model;

import jakarta.persistence.Column;
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
  @Column(name = "SAGA_DATA_TYPE")
  private String sagaDataType;
  @Column(name = "SAGA_DATA_JSON")
  private String sagaDataJSON;

}
