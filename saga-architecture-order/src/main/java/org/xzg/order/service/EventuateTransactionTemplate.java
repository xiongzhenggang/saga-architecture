package org.xzg.order.service;

import org.xzg.order.domain.Order;

import java.util.function.Supplier;

public interface EventuateTransactionTemplate {
    Order executeInTransaction(Supplier<Order> consumer);
}
