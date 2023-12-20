package com.xzg.goods.dao;

import com.xzg.goods.entity.Goods;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: GoodsRepository
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
 * 12/20/2023   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 12/20/2023
 * </p>
 */
public interface GoodsRepository extends CrudRepository<Goods, Long> {
}
