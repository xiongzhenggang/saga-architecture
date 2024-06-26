package com.xzg.account.dao;

import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaMessageRepository;
import com.xzg.orchestrator.kit.orchestration.saga.model.SagaMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.order.sagas.dao
 * @className: SagaMessageDao
 * @author: xzg
 * @description: TODO
 * @date: 23/6/2024-下午 6:01
 * @version: 1.0
 */
@Slf4j
@Repository
public class SagaMessageRepositoryImpl implements SagaMessageRepository {

    @Resource
    private SagaMessageDaoRepository daoRepository;
    @Override
    public SagaMessage save(SagaMessage sagaMessage) {
        log.info("save saga transaction message:{}",sagaMessage);
        return daoRepository.save(sagaMessage);
    }

    @Override
    public void update(SagaMessage sagaMessage) {
        log.info("update sagaMessage：{}",sagaMessage);
        daoRepository.save(sagaMessage);
    }
}


    