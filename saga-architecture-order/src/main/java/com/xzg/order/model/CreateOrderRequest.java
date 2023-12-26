package com.xzg.order.model;

import com.xzg.library.config.infrastructure.model.Money;
import lombok.Data;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: CreateOrderRequest
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
 * 12/13/2023   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 12/13/2023
 * </p>
 */
@Data
public class CreateOrderRequest {
    private Money orderTotal;
    private Long customerId;
    private Long goodsId;
    private Integer goodsTotal;
}
