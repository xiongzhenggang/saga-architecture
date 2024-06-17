package com.xzg.order.service;


import com.xzg.orchestrator.kit.common.enums.RejectionReason;
import com.xzg.order.dao.OrderRepository;
import com.xzg.order.domain.Order;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author xiongzhenggang
 */
@Service
public class OrderService {

  @Resource
  private OrderRepository orderRepository;

    /**
     * 初始创建原始订单
     * @param orderDetails
     * @return
     */
  public Order createOrder(OrderDetails orderDetails) {
    Order order = Order.createOrder(orderDetails);
    orderRepository.save(order);
    return order;
  }

  /**
   * 更新订单状态为完成
   * @param orderId
   */
  public void approveOrder(Long orderId) {
     orderRepository
            .findById(orderId)
             .ifPresent(o -> {
               o.approve();
               orderRepository.save(o);
             });
  }

  /**
   * 更新订单为拒绝
   * @param orderId
   * @param rejectionReason
   */
  public void rejectOrder(Long orderId, RejectionReason rejectionReason) {
    orderRepository
            .findById(orderId)
            .ifPresent(o -> {
              o.reject(rejectionReason);
              orderRepository.save(o);
            });
  }

}
