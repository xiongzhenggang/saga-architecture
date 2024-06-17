package com.xzg.order.dao;

import com.xzg.order.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByOrderDetailsUserId(Long customerId);

}
