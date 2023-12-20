package com.xzg.account.domain;

import com.xzg.account.exception.CustomerCreditLimitExceededException;
import com.xzg.library.config.infrastructure.model.Money;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name="Customer")
@Access(AccessType.FIELD)
@Data
public class Customer {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

//  private BigDecimal amount;
  @Embedded
  private Money creditLimit;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;
//
//  @ElementCollection
//  private Map<Long, Money> creditReservations;

  Money availableCredit() {
//    return creditLimit.subtract(creditReservations.values().stream().reduce(Money.ZERO, Money::add));
    return creditLimit;

  }

  public Customer() {
  }
  public Customer(String name, Money creditLimit) {
    this.name = name;
    this.creditLimit = creditLimit;
//    this.creditReservations = Collections.emptyMap();
  }

  /**
   * 余额是否够用
   * @param orderId
   * @param orderTotal
   */
  public void reserveCredit(Long orderId, Money orderTotal) {
    if (availableCredit().isGreaterThanOrEqual(orderTotal)) {
//      creditReservations.put(orderId, orderTotal);
        this.creditLimit = this.creditLimit.subtract(orderTotal);
    } else {
        throw new CustomerCreditLimitExceededException();
    }
  }
}
