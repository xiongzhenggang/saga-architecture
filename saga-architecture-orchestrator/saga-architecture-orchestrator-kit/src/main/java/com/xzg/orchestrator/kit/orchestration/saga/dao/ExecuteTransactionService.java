package com.xzg.orchestrator.kit.orchestration.saga.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.Callable;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.orchestration.saga.dao
 * @className: ExcuteTransactionTemplate
 * @author: xzg
 * @description: TODO
 * @date: 11/8/2024-下午 9:22
 * @version: 1.0
 */
@Component
public class ExecuteTransactionService {

    @Autowired
    private PlatformTransactionManager transactionManager;

    private ThreadLocal<TransactionStatus> transactionStatus = new ThreadLocal<>();

    public void begin(){
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionStatus.set(transactionManager.getTransaction(transactionDefinition));
    }

    public void commit(){
        transactionManager.commit(transactionStatus.get());
        transactionStatus.remove();
    }

    public void rollback(){
        transactionManager.rollback(transactionStatus.get());
        transactionStatus.remove();
    }

    /**
     * 执行事务
     * @param callback
     * @return
     * @param <V>
     */
    public <V>  V execute(Callable<V> callback){
        begin();
        try {
            V result = callback.call();
            commit();
            return result;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException(e);
        }
    }


}


    