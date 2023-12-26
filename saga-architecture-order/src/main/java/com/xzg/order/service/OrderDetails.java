package com.xzg.order.service;


import com.xzg.library.config.infrastructure.model.Money;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 声明该类是可嵌入的
 */
@Builder
@Data
@Embeddable
public class OrderDetails {
  private Long customerId;
  @Embedded
  private Money orderTotal;
  private Long goodsId;
  private Integer goodsTotal;
  public OrderDetails() {
  }

}
