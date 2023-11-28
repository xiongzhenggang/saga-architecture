package com.xzg.orchestrator.kit.orchestration.saga.model;

import com.xzg.orchestrator.kit.orchestration.DestinationAndResource;
import com.xzg.orchestrator.kit.orchestration.SerializedSagaData;
import lombok.Data;

import java.util.Set;

/**
 * @author xiongzhenggang
 */
@Data
public class SagaInstance {

  private String sagaType;
  private String id;
  private String lastRequestId;
  private SerializedSagaData serializedSagaData;
  private String stateName;
  private Set<DestinationAndResource> destinationsAndResources;
  private Boolean endState = false;
  private Boolean compensating = false;
  private Boolean failed = false;


  public SagaInstance(String sagaType, String sagaId, String stateName, String lastRequestId, SerializedSagaData serializedSagaData, Set<DestinationAndResource> destinationsAndResources,
                      boolean endState, boolean compensating, boolean failed) {
    this(sagaType, sagaId, stateName, lastRequestId, serializedSagaData, destinationsAndResources);

    this.endState = endState;
    this.compensating = compensating;
    this.failed = failed;
  }
  public SagaInstance(String sagaType, String sagaId, String stateName, String lastRequestId, SerializedSagaData serializedSagaData, Set<DestinationAndResource> destinationsAndResources) {
    this.sagaType = sagaType;
    this.id = sagaId;
    this.stateName = stateName;
    this.lastRequestId = lastRequestId;
    this.serializedSagaData = serializedSagaData;
    this.destinationsAndResources = destinationsAndResources;
  }

  public SerializedSagaData getSerializedSagaData() {
    return serializedSagaData;
  }

  public void setSerializedSagaData(SerializedSagaData serializedSagaData) {
    this.serializedSagaData = serializedSagaData;
  }

  public void addDestinationsAndResources(Set<DestinationAndResource> destinationAndResources) {
    this.destinationsAndResources.addAll(destinationAndResources);
  }


}
