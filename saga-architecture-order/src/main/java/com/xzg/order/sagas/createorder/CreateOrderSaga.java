package com.xzg.order.sagas.createorder;


import com.xzg.library.config.infrastructure.model.Money;
import com.xzg.orchestrator.kit.common.enums.RejectionReason;
import com.xzg.orchestrator.kit.participant.result.CustomerCreditLimitExceeded;
import com.xzg.orchestrator.kit.participant.result.CustomerNotFound;
import com.xzg.orchestrator.kit.participant.result.GoodsNotFound;
import com.xzg.orchestrator.kit.participant.result.GoodsStockLimit;
import com.xzg.orchestrator.kit.command.CommandWithDestination;
import com.xzg.orchestrator.kit.orchestration.dsl.SimpleSaga;
import com.xzg.orchestrator.kit.orchestration.saga.SagaDefinition;
import com.xzg.order.domain.Order;
import com.xzg.order.sagas.participants.proxy.AccountServiceProxy;
import com.xzg.order.sagas.participants.proxy.GoodsServiceProxy;
import com.xzg.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiongzhenggang
 * @Date 2023/12/19
 */
@Slf4j
public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {

  private OrderService orderService;
  private AccountServiceProxy customerService;
  private GoodsServiceProxy goodsServiceProxy;
  public CreateOrderSaga(OrderService orderService, AccountServiceProxy customerService,GoodsServiceProxy goodsServiceProxy) {
    this.orderService = orderService;
    this.customerService = customerService;
    this.goodsServiceProxy = goodsServiceProxy;
  }

  /**
   * 定义订单saga状态机转换流程
   *
   */
  private SagaDefinition<CreateOrderSagaData> sagaDefinition =
          step()
                  .invokeLocal(this::create)
                  .withCompensation(this::reject)
                  .step()
                  .invokeParticipant(this::reserveGoods)
                  .onReply(GoodsStockLimit.class,this::handleGoodsLimit)
                  .onReply(GoodsNotFound.class,this::handleGoodsNotFound)
                  //补偿操作要定义
                  .withCompensation(this::releaseGoods)
                  .step()
                  .invokeParticipant(this::reserveCredit)
                  .onReply(CustomerNotFound.class, this::handleCustomerNotFound)
                  .onReply(CustomerCreditLimitExceeded.class, this::handleCustomerCreditLimitExceeded)
                  .step()
                  //最终正向流程执行完成为成功
                  .invokeLocal(this::approve)
                  .build();


  private void handleGoodsLimit(CreateOrderSagaData data, GoodsStockLimit reply) {
    data.setRejectionReason(RejectionReason.INSUFFICIENT_GOODS);
  }
  private void handleGoodsNotFound(CreateOrderSagaData data, GoodsNotFound reply) {
    data.setRejectionReason(RejectionReason.UNKNOWN_GOODS);
  }
  private void handleCustomerNotFound(CreateOrderSagaData data, CustomerNotFound reply) {
    data.setRejectionReason(RejectionReason.UNKNOWN_CUSTOMER);
  }

  private void handleCustomerCreditLimitExceeded(CreateOrderSagaData data, CustomerCreditLimitExceeded reply) {
    data.setRejectionReason(RejectionReason.INSUFFICIENT_CREDIT);
  }


  @Override
  public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
    return this.sagaDefinition;
  }

  private void create(CreateOrderSagaData data) {
    Order order = orderService.createOrder(data.getOrderDetails());
    data.setOrderId(order.getId());
  }

  /**
   * 商品扣将
   * @param data
   * @return
   */
  private CommandWithDestination reserveGoods(CreateOrderSagaData data) {
    log.info("====》正向流程扣将商品操作：{}",data);
    long orderId = data.getOrderId();
    Long goodsId = data.getOrderDetails().getGoodsId();
    Integer goodsTotal = data.getOrderDetails().getGoodsTotal();
    return goodsServiceProxy.reserveGoodsStock(orderId, goodsId, goodsTotal);
  }
  /**
   * 商品释放
   * @param data
   * @return
   */
  private CommandWithDestination releaseGoods(CreateOrderSagaData data) {
    log.info("====》反向流程补偿操作，给商品加上扣减得数量：{}",data);
    long orderId = data.getOrderId();
    Long goodsId = data.getOrderDetails().getGoodsId();
    Integer goodsTotal = data.getOrderDetails().getGoodsTotal();
    return goodsServiceProxy.releaseGoodsStock(orderId, goodsId, goodsTotal);
  }
  /**
   * 用户账户扣款服务
   * @param data
   * @return
   */
  private CommandWithDestination reserveCredit(CreateOrderSagaData data) {
    log.info("====》正向流程扣减信用卡额度：{}",data);
    long orderId = data.getOrderId();
    Long customerId = data.getOrderDetails().getCustomerId();
    Money orderTotal = data.getOrderDetails().getOrderTotal();
    return customerService.reserveCredit(orderId, customerId, orderTotal);
  }

  private void approve(CreateOrderSagaData data) {
    log.info("====》正向流程执行成功，订单完成：{}",data);
    orderService.approveOrder(data.getOrderId());
  }

  private void reject(CreateOrderSagaData data) {
    log.info("====》逆向流程补偿，订单失败：{}",data);
    orderService.rejectOrder(data.getOrderId(), data.getRejectionReason());
  }
}
