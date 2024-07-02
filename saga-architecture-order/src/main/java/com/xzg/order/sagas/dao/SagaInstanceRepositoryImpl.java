package com.xzg.order.sagas.dao;

import com.xzg.orchestrator.kit.orchestration.saga.model.SagaInstance;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaInstanceRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: SagaInstanceRepositoryImpl
 * </p>
 * <p>
 * <b>Class description</b>:
 *
 * </p>
 * <p>
 * <b>Author</b>: xiongzhenggang
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 11/28/2023   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 11/28/2023
 * </p>
 */
@Slf4j
@Repository
public class SagaInstanceRepositoryImpl implements SagaInstanceRepository {

    @Resource
    private SagaInstanceDaoRepository sagaRepository;
    @Override
    public void save(SagaInstance sagaInstance) {
        log.info("insert saga：{}",sagaInstance);
        sagaRepository.save(sagaInstance);
    }

    @Override
    public SagaInstance find(String sagaType, String sagaId) {
        return sagaRepository.findByIdAndSagaType(sagaId,sagaType);
    }

    @Override
    public void update(SagaInstance sagaInstance) {
        log.info("update saga：{}",sagaInstance);
        sagaRepository.save(sagaInstance);
    }
}
