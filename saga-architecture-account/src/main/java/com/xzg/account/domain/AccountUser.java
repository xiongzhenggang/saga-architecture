package com.xzg.account.domain;

import com.xzg.account.exception.CustomerCreditLimitExceededException;
import com.xzg.library.config.infrastructure.model.Money;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * @author xiongzhenggang
 */
@Entity
@Table(name="ACCOUNT_USER")
@Access(AccessType.FIELD)
@Data
public class AccountUser {

  @Id
  @GeneratedValue
  private Long id;

  private String userName;

  private Long userId;

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

  public AccountUser() {
  }
  public AccountUser(String name,Long userId, Money creditLimit) {
    this.userName = name;
    this.creditLimit = creditLimit;
    this.userId=userId;
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
