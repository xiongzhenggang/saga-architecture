package com.xzg.goods.dao;

import com.xzg.orchestrator.kit.orchestration.saga.model.SagaMessage;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: SagaMessageRepository
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
 * 1/3/2024   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 1/3/2024
 * </p>
 */
public interface SagaMessageDaoRepository extends CrudRepository<SagaMessage, String>, Serializable {
}
