package com.xzg.order.dao;

import com.xzg.order.domain.Order;
import com.xzg.order.domain.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoImpl implements OrderDao {

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public Order findById(long id) {
    return orderRepository
            .findById(id)
            .orElseThrow(() ->
                    new IllegalArgumentException(String.format("Order with id=%s is not found", id)));
  }

  @Override
  public Order save(Order order) {
    return orderRepository.save(order);
  }
}
