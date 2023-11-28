package com.xzg.orchestrator.kit.orchestration.saga.impl;

import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaRepository;
import com.xzg.orchestrator.kit.orchestration.saga.model.SagaInstance;
import com.xzg.orchestrator.kit.orchestration.saga.SagaInstanceRepository;
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
    private SagaRepository sagaRepository;
    @Override
    public void save(SagaInstance sagaInstance) {
//        sagaRepository.sa
        log.info("保存saga：{}",sagaInstance);
    }

    @Override
    public SagaInstance find(String sagaType, String sagaId) {
        return null;
    }

    @Override
    public void update(SagaInstance sagaInstance) {

    }
}
