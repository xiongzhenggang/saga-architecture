package com.xzg.goods.service;


import com.xzg.goods.dao.GoodsDao;
import com.xzg.goods.entity.Goods;
import com.xzg.goods.exception.GoodsStockLimitExceededException;
import com.xzg.orchestrator.kit.business.resultexception.GoodsNotFound;
import com.xzg.orchestrator.kit.business.resultexception.GoodsStockLimit;
import com.xzg.orchestrator.kit.business.resultexception.GoodsStockReserve;
import com.xzg.orchestrator.kit.command.CommandHandlers;
import com.xzg.orchestrator.kit.command.business.ReserveGoodsStockCommand;
import com.xzg.orchestrator.kit.common.SagaServiceEnum;
import com.xzg.orchestrator.kit.event.CommandMessage;
import com.xzg.orchestrator.kit.event.Message;
import com.xzg.orchestrator.kit.participant.SagaCommandHandlersBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.xzg.orchestrator.kit.command.CommandHandlerReplyBuilder.withFailure;
import static com.xzg.orchestrator.kit.command.CommandHandlerReplyBuilder.withSuccess;

/**
 * @author xiongzhenggang
 * @Date 2023/12/19
 *
 */
@Slf4j
public class GoodsCommandHandler {

  private GoodsDao goodsDao;

  public GoodsCommandHandler(GoodsDao goodsDao) {
    this.goodsDao = goodsDao;
  }

  /**
   * 定义命令，收到状体变更
   *  发送到order
   * @return
   */
  public CommandHandlers commandHandlerDefinitions() {
    return SagaCommandHandlersBuilder
            .fromChannel(SagaServiceEnum.GOODS_SERVICE.getType())
            .onMessage(ReserveGoodsStockCommand.class, this::reserveGoodsStock)
            .build();
  }

  /**
   * 账户信用卡是否够用
   * @param cm
   * @return
   */
  public Message reserveGoodsStock(CommandMessage<ReserveGoodsStockCommand> cm) {
    ReserveGoodsStockCommand cmd = cm.getCommand();
    long goodsId = cmd.getGoodsId();
    try {
      Optional<Goods> goods = goodsDao.findById(goodsId);
      if(!goods.isPresent()){
        return withFailure(new GoodsNotFound());
      }
      Goods goods1 = goods.get();
      goods1.reserveGoodsStock(cmd.getGoodsTotal());
      //账户余额扣减
      goods1.setUpdateTime(LocalDateTime.now());
      goodsDao.saveGoods(goods1);
      return withSuccess(new GoodsStockReserve());
    } catch (GoodsStockLimitExceededException e) {
      log.error("库存不足：{}",e);
      return withFailure(new GoodsStockLimit());
    }
  }


}
