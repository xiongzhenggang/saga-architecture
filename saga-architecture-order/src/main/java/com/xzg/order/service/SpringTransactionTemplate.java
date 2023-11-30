package com.xzg.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.order.service
 * @className: SpringTransactionTemplate
 * @author: xzg
 * @description: TODO
 * @date: 30/11/2023-下午 8:37
 * @version: 1.0
 */
@Service
public class SpringTransactionTemplate implements EventuateTransactionTemplate {

    private TransactionTemplate transactionTemplate;

    public SpringTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public <T> T executeInTransaction(Supplier<T> callback) {
        return transactionTemplate.execute(status -> callback.get());
    }
}

    