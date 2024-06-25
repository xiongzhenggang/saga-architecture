package com.xzg.goods.service;


import com.xzg.goods.dao.GoodsDao;
import com.xzg.goods.entity.Goods;
import com.xzg.goods.exception.GoodsStockLimitExceededException;
import com.xzg.orchestrator.kit.command.CommandHandlers;
import com.xzg.orchestrator.kit.command.business.ReleaseGoodsStockCommand;
import com.xzg.orchestrator.kit.command.business.ReserveGoodsStockCommand;
import com.xzg.orchestrator.kit.common.SagaServiceEnum;
import com.xzg.orchestrator.kit.message.CommandMessage;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.participant.SagaCommandHandlersBuilder;
import com.xzg.orchestrator.kit.participant.result.GoodsLocalTransCompensation;
import com.xzg.orchestrator.kit.participant.result.GoodsNotFound;
import com.xzg.orchestrator.kit.participant.result.GoodsStockLimit;
import com.xzg.orchestrator.kit.participant.result.GoodsStockReserve;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.xzg.orchestrator.kit.command.CommandHandlerReplyBuilder.withFailure;
import static com.xzg.orchestrator.kit.command.CommandHandlerReplyBuilder.withSuccess;

/**
 * @author xiongzhenggang
 * @Date 2023/12/19
 */
@Slf4j
public class GoodsCommandHandler {

    private GoodsDao goodsDao;

    public GoodsCommandHandler(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    /**
     * 定义收到正向流程中保存商品
     * 正向流程从订单服务发送过来的 order->goods->account
     *
     * @return
     */
    public CommandHandlers commandHandlerDefinitions() {
        return SagaCommandHandlersBuilder
                .fromChannel(SagaServiceEnum.GOODS_SERVICE.getType())
                .onMessage(ReserveGoodsStockCommand.class, this::reserveGoodsStock)
                .onMessage(ReleaseGoodsStockCommand.class, this::releaseGoodsStock)
                .build();
    }

    /**
     * 补偿商品库存
     * @param cm
     * @return
     */
    public Message releaseGoodsStock(CommandMessage<ReleaseGoodsStockCommand> cm) {
        long goodsId = cm.getCommand().getGoodsId();
        Integer goodsTotal = cm.getCommand().getGoodsTotal();
        Optional<Goods> goods = goodsDao.findById(goodsId);
        goods.ifPresent(s->{
            //补偿数量
            s.setStock(s.getStock()+goodsTotal);
            s.setUpdateTime(LocalDateTime.now());
            goodsDao.saveGoods(s);
        });
        //补偿成功
        return withSuccess(new GoodsLocalTransCompensation());
    }
    /**
     * 账户信用卡是否够用
     *
     * @param cm
     * @return
     */
    public Message reserveGoodsStock(CommandMessage<ReserveGoodsStockCommand> cm) {
        ReserveGoodsStockCommand cmd = cm.getCommand();
        long goodsId = cmd.getGoodsId();
        try {
            Optional<Goods> goods = goodsDao.findById(goodsId);
            if (goods.isEmpty()) {
                return withFailure(new GoodsNotFound());
            }
            Goods goods1 = goods.get();
            goods1.reserveGoodsStock(cmd.getGoodsTotal());
            //账户余额扣减
            goods1.setUpdateTime(LocalDateTime.now());
            goodsDao.saveGoods(goods1);
            return withSuccess(new GoodsStockReserve());
        } catch (GoodsStockLimitExceededException e) {
            log.error("库存不足：{}", e.getMessage());
            return withFailure(new GoodsStockLimit());
        }
    }

}
