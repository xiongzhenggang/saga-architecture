package com.xzg.library.config.infrastructure.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.model
 * @className: Money
 * @author: xzg
 * @description: TODO
 * @date: 16/11/2023-下午 8:16
 * @version: 1.0
 */
@Embeddable
@Access(AccessType.FIELD)
public class Money {

    public static final Money ZERO = new Money(0);
    private BigDecimal amount;

    public Money() {
    }

    public Money(int i) {
        this.amount = new BigDecimal(i);
    }
    public Money(String s) {
        this.amount = new BigDecimal(s);
    }


    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount.compareTo(other.amount) >= 0;
    }

    public Money add(Money other) {
        return new Money(amount.add(other.amount));
    }
    public Money subtract(Money other) {
        return new Money(amount.subtract(other.amount));
    }
}
    