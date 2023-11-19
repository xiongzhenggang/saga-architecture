package com.xzg.order.service;


import com.xzg.orchestrator.kit.business.enums.RejectionReason;
import com.xzg.orchestrator.kit.event.ResultWithEvents;
import com.xzg.orchestrator.kit.orchestration.saga.SagaInstanceFactory;
import com.xzg.order.dao.OrderRepository;
import com.xzg.order.domain.Order;
import com.xzg.order.domain.OrderDao;
import com.xzg.order.sagas.createorder.CreateOrderSaga;
import com.xzg.order.sagas.createorder.CreateOrderSagaData;
import com.xzg.order.sagas.createorder.LocalCreateOrderSaga;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xzg.order.sagas.createorder.LocalCreateOrderSagaData;

@Service
public class OrderService {

  @Resource
  private OrderDao orderDao;
  @Resource
  private EventuateTransactionTemplate eventuateTransactionTemplate;
  @Resource
  private SagaInstanceFactory sagaInstanceFactory;
  @Resource
  private LocalCreateOrderSaga localCreateOrderSaga;
  @Resource
  private CreateOrderSaga createOrderSaga;
  @Resource
  private OrderRepository orderRepository;

  public Order createOrder(OrderDetails orderDetails) {
    return eventuateTransactionTemplate.executeInTransaction(() -> {
      ResultWithEvents<Order> oe = Order.createOrder(orderDetails);
      Order order = oe.result;
      orderDao.save(order);
      CreateOrderSagaData data = new CreateOrderSagaData(order.getId(), orderDetails);
      sagaInstanceFactory.create(createOrderSaga, data);
      return order;
    });
  }

  public Order localCreateOrder(OrderDetails orderDetails) {
    return eventuateTransactionTemplate.executeInTransaction(() -> {
      LocalCreateOrderSagaData data = new LocalCreateOrderSagaData(orderDetails);
      sagaInstanceFactory.create(localCreateOrderSaga, data);
      return orderDao.findById(data.getOrderId());
    });
  }
  public void approveOrder(Long orderId) {
    orderRepository.findById(orderId).get().approve();
  }

  public void rejectOrder(Long orderId, RejectionReason rejectionReason) {
    orderRepository.findById(orderId).get().reject(rejectionReason);
  }
}
