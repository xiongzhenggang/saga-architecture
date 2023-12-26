package com.xzg.order.sagas.createorder;


import com.xzg.library.config.infrastructure.model.Money;
import com.xzg.orchestrator.kit.business.enums.RejectionReason;
import com.xzg.orchestrator.kit.business.resultexception.CustomerCreditLimitExceeded;
import com.xzg.orchestrator.kit.business.resultexception.CustomerNotFound;
import com.xzg.orchestrator.kit.business.resultexception.GoodsStockLimit;
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
  public CreateOrderSaga(OrderService orderService, AccountServiceProxy customerService) {
    this.orderService = orderService;
    this.customerService = customerService;
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
                  .withCompensation(this::releaseGoods)
                  .step()
                  .invokeParticipant(this::reserveCredit)
                  .onReply(CustomerNotFound.class, this::handleCustomerNotFound)
                  .onReply(CustomerCreditLimitExceeded.class, this::handleCustomerCreditLimitExceeded)
                  .step()
                  .invokeLocal(this::approve)
                  .build();


  private void handleGoodsLimit(CreateOrderSagaData data, GoodsStockLimit reply) {
    data.setRejectionReason(RejectionReason.GOODS_LIMIT);
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
    long orderId = data.getOrderId();
    Long customerId = data.getOrderDetails().getCustomerId();
    Money orderTotal = data.getOrderDetails().getOrderTotal();
    return customerService.reserveCredit(orderId, customerId, orderTotal);
  }

  private void approve(CreateOrderSagaData data) {
    orderService.approveOrder(data.getOrderId());
  }

  private void reject(CreateOrderSagaData data) {
    orderService.rejectOrder(data.getOrderId(), data.getRejectionReason());
  }
}
