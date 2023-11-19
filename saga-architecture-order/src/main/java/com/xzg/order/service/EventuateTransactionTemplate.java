package com.xzg.order.service;

import com.xzg.order.domain.Order;

import java.util.function.Supplier;

public interface EventuateTransactionTemplate {
    Order executeInTransaction(Supplier<Order> consumer);
}
