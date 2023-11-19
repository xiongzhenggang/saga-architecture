package com.xzg.order.domain;

public interface OrderDao {
  Order findById(long id);
  Order save(Order order);
}
