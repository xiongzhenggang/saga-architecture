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
 Embeddable表明该类虽然很复杂，但不需要在数据库中拥有其表。相反，它的字段可以直接嵌入到另一个实体中 声明该类是可嵌入的
 * @author xiongzhenggang
 */
@Builder
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
  private Long userId;
  @Embedded
  private Money orderTotal;
  private Long goodsId;
  private Integer goodsTotal;
}
