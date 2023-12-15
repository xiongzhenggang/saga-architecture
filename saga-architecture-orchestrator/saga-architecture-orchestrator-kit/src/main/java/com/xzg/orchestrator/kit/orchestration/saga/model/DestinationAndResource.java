package com.xzg.orchestrator.kit.orchestration.saga.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="destination_resource")
public class DestinationAndResource {
  public DestinationAndResource(String destination, String resource) {
    this.destination = destination;
    this.resource = resource;
  }
  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "destination")
  private String destination;
  @Column(name = "resource")
  private String resource;
  @Column(name = "saga_id")
  private String sagaId;
  // 关联关系多对一，级联关系，可更新，持久化， 获取方式懒加载；
  @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
          fetch = FetchType.LAZY)
  // 以 group_id 为外键进行关联，referencedColumnName 默认为被关联实体类主键，可以忽略；
  @JoinColumn(name="saga_id",referencedColumnName = "id",insertable=false, updatable=false)
  @JsonIgnore
  private SagaInstance sagaInstance;



}
