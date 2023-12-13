package com.xzg.order.model;

import com.xzg.orchestrator.kit.business.enums.RejectionReason;
import com.xzg.order.domain.OrderState;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: GetOrderResponse
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
@Builder
public class GetOrderResponse {
    private Long orderId;
    private OrderState orderState;
    private RejectionReason rejectionReason;
}
