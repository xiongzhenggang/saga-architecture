package com.xzg.order.service;


import com.xzg.orchestrator.kit.business.enums.RejectionReason;
import com.xzg.order.dao.OrderRepository;
import com.xzg.order.domain.Order;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Resource
  private OrderRepository orderRepository;

  public Order createOrder(OrderDetails orderDetails) {
    Order order = Order.createOrder(orderDetails);
    orderRepository.save(order);
    return order;
  }

  public void approveOrder(Long orderId) {
    orderRepository.findById(orderId).get().approve();
  }

  public void rejectOrder(Long orderId, RejectionReason rejectionReason) {
    orderRepository.findById(orderId).get().reject(rejectionReason);
  }

//  public Order createOrder1(OrderDetails orderDetails) {
//    return eventuateTransactionTemplate.executeInTransaction(() -> {
//      CreateOrderSagaData data = new CreateOrderSagaData(order.getId(), orderDetails);
//      sagaInstanceFactory.create(createOrderSaga, data);
//      return order;
//    });
//  }
//
//  public Order localCreateOrder(OrderDetails orderDetails) {
//    return eventuateTransactionTemplate.executeInTransaction(() -> {
//      LocalCreateOrderSagaData data = new LocalCreateOrderSagaData(orderDetails);
//      sagaInstanceFactory.create(localCreateOrderSaga, data);
//      return orderDao.findById(data.getOrderId());
//    });
//  }
//  public void approveOrder(Long orderId) {
//    orderRepository.findById(orderId).get().approve();
//  }
//
//  public void rejectOrder(Long orderId, RejectionReason rejectionReason) {
//    orderRepository.findById(orderId).get().reject(rejectionReason);
//  }
  /**
   * Order
   */
}
