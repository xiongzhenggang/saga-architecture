package com.xzg.order.sagas.participants.proxy;

import com.xzg.orchestrator.kit.command.CommandWithDestination;
import com.xzg.orchestrator.kit.command.business.ReleaseGoodsStockCommand;
import com.xzg.orchestrator.kit.command.business.ReserveGoodsStockCommand;
import com.xzg.orchestrator.kit.common.SagaServiceEnum;
import org.springframework.stereotype.Component;

import static com.xzg.orchestrator.kit.command.CommandWithDestinationBuilder.send;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: GoodsServiceProxy
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
 * 12/26/2023   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 12/26/2023
 * </p>
 */
@Component
public class GoodsServiceProxy {
    /**
     * 发送商品服务校验并扣减库存
     * @param orderId
     * @param goodsId
     * @param goodsTotal
     * @return
     */
    public CommandWithDestination reserveGoodsStock(long orderId, Long goodsId, Integer goodsTotal) {
        return send(ReserveGoodsStockCommand.builder()
                .goodsId(goodsId)
                .goodsTotal(goodsTotal)
                .orderId(orderId).build())
                .to(SagaServiceEnum.GOODS_SERVICE.getType())
                .build();
    }

    /**
     * 支付失败释放购买的商品进入库存
     * @param orderId
     * @param goodsId
     * @param goodsTotal
     * @return
     */
    public CommandWithDestination releaseGoodsStock(long orderId, Long goodsId, Integer goodsTotal) {
        return send(ReleaseGoodsStockCommand.builder()
                .goodsId(goodsId)
                .goodsTotal(goodsTotal)
                .orderId(orderId).build())
                .to(SagaServiceEnum.GOODS_SERVICE.getType())
                .build();
    }
}
