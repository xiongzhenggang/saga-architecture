package com.xzg.order.sagas.dao;

import com.xzg.orchestrator.kit.orchestration.saga.model.SagaInstance;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: SagaRepository
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
public interface SagaInstanceDaoRepository extends CrudRepository<SagaInstance, String>, Serializable {

    SagaInstance findByIdAndSagaType(String sagaType, String sagaId);
}
