package com.xzg.orchestrator.kit.orchestration;


import lombok.Data;

@Data
public class DestinationAndResource {
  private String destination;
  private String resource;

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//
//    if (o == null || getClass() != o.getClass()) return false;
//
//    DestinationAndResource that = (DestinationAndResource) o;
//
//    return new EqualsBuilder()
//            .append(destination, that.destination)
//            .append(resource, that.resource)
//            .isEquals();
//  }
//
//  @Override
//  public int hashCode() {
//    return new HashCodeBuilder(17, 37)
//            .append(destination)
//            .append(resource)
//            .toHashCode();
//  }

  public DestinationAndResource(String destination, String resource) {
    this.destination = destination;
    this.resource = resource;
  }

  public String getDestination() {
    return destination;
  }

  public String getResource() {
    return resource;
  }
}
