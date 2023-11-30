package com.xzg.order.service;

import java.util.function.Supplier;

public interface EventuateTransactionTemplate {
    <T> T executeInTransaction(Supplier<T> callback);
}
