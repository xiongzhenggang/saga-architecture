package com.xzg.orchestrator.kit.command.business;

import com.xzg.orchestrator.kit.command.Command;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: ReleaseGoodsStockCommand
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
@Data
@Builder
public class ReleaseGoodsStockCommand implements Command {
    private long orderId;
    /**
     * 购买商品数量失败补偿数量
     */
    private Integer goodsTotal;
    private long customerId;
}
