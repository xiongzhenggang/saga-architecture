package com.xzg.orchestrator.kit.orchestration.saga.dao;

import com.xzg.orchestrator.kit.orchestration.saga.model.SagaMessage;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.orchestration.saga.dao
 * @className: SagaMessageRepostory
 * @author: xzg
 * @description: TODO
 * @date: 23/6/2024-下午 5:57
 * @version: 1.0
 */
public interface SagaMessageRepository {
    SagaMessage save(SagaMessage sagaMessage);
    void update(SagaMessage sagaMessage);
}


    