package com.xzg.orchestrator.kit.orchestration.saga;

import com.xzg.orchestrator.kit.orchestration.DestinationAndResource;
import com.xzg.orchestrator.kit.orchestration.SerializedSagaData;

import java.util.Set;

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

  @Override
  public String toString() {
    return "SagaInstance{" +
            "sagaType='" + sagaType + '\'' +
            ", id='" + id + '\'' +
            ", lastRequestId='" + lastRequestId + '\'' +
            ", serializedSagaData=" + serializedSagaData +
            ", stateName='" + stateName + '\'' +
            ", destinationsAndResources=" + destinationsAndResources +
            ", endState=" + endState +
            ", compensating=" + compensating +
            ", failed=" + failed +
            '}';
  }

  public void setSagaType(String sagaType) {
    this.sagaType = sagaType;
  }

  public String getStateName() {
    return stateName;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

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


  public void setId(String id) {
    this.id = id;
  }

  public String getSagaType() {
    return sagaType;
  }

  public String getId() {
    return id;
  }

  public String getLastRequestId() {
    return lastRequestId;
  }

  public void setLastRequestId(String requestId) {
    this.lastRequestId = requestId;
  }

  public void addDestinationsAndResources(Set<DestinationAndResource> destinationAndResources) {
    this.destinationsAndResources.addAll(destinationAndResources);
  }

  public Set<DestinationAndResource> getDestinationsAndResources() {
    return destinationsAndResources;
  }

  public void setEndState(Boolean endState) {
    this.endState = endState;
  }

  public Boolean isEndState() {
    return endState;
  }

  public void setCompensating(Boolean compensating) {
    this.compensating = compensating;
  }

  public Boolean isCompensating() {
    return compensating;
  }

  public void setFailed(boolean failed) {
    this.failed = failed;
  }

  public Boolean isFailed() {
    return failed;
  }
}
