package com.xzg.orchestrator.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author xiongzhenggang
 */
@Data
@Table(name="saga_instance")
@Entity
public class SagaInstance {
  @Id
  @Column(name="id")
  private String id;
  @Column(name = "saga_type")
  private String sagaType;
  private String sagaName;
  @Column(name = "last_request_id")
  private String lastRequestId;
  @Column(name = "state_name")
  private String stateName;
  @Embedded
  private SerializedSagaData serializedSagaData;
  @OneToMany(targetEntity = DestinationAndResource.class,mappedBy = "sagaId",
          fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  private List<DestinationAndResource> destinationsAndResources;
  private Boolean endState = false;
  private Boolean compensating = false;
  private Boolean failed = false;
  public void addDestinationsAndResources(Set<DestinationAndResource> destinationAndResources) {
    this.destinationsAndResources.addAll(destinationAndResources);
  }


}
