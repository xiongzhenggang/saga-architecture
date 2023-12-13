package com.xzg.order.service;

import com.xzg.orchestrator.kit.orchestration.saga.SagaInstanceFactory;
import com.xzg.order.dao.OrderRepository;
import com.xzg.order.domain.Order;
import com.xzg.order.sagas.createorder.CreateOrderSaga;
import com.xzg.order.sagas.createorder.CreateOrderSagaData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: OrderSagaService
 * </p>
 * <p>
 * <b>Class description</b>:
 *
 * </p>
 * <p>
 * <b>Author</b>: xiongzhenggang
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 12/13/2023   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 12/13/2023
 * </p>
 */
public class OrderSagaService {

    private final OrderRepository orderRepository;

    private final SagaInstanceFactory sagaInstanceFactory;

    private final CreateOrderSaga createOrderSaga;

    public OrderSagaService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
        this.orderRepository = orderRepository;
        this.sagaInstanceFactory = sagaInstanceFactory;
        this.createOrderSaga = createOrderSaga;
    }

    @Transactional
    public Order createOrder(OrderDetails orderDetails) {
        CreateOrderSagaData data = new CreateOrderSagaData(orderDetails);
        sagaInstanceFactory.create(createOrderSaga, data);
        return orderRepository.findById(data.getOrderId()).get();
    }
}
