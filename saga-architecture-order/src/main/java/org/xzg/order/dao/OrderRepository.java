package org.xzg.order.dao;

import org.springframework.data.repository.CrudRepository;
import org.xzg.order.domain.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByOrderDetailsCustomerId(Long customerId);

}
