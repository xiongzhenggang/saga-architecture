package com.xzg.order.api;

import com.xzg.library.config.infrastructure.model.CommonResponse;
import com.xzg.order.dao.OrderRepository;
import com.xzg.order.domain.Order;
import com.xzg.order.model.CreateOrderRequest;
import com.xzg.order.model.GetOrderResponse;
import com.xzg.order.service.OrderDetails;
import com.xzg.order.sagas.service.OrderSagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: OrderController
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
@RestController
public class OrderController {

    private OrderSagaService orderSagaService;
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderSagaService orderSagaService, OrderRepository orderRepository) {
        this.orderSagaService = orderSagaService;
        this.orderRepository = orderRepository;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public CommonResponse<String> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        Order order = orderSagaService.createOrder(
                OrderDetails.builder()
                        .userId(createOrderRequest.getCustomerId())
                        .goodsTotal(createOrderRequest.getGoodsTotal())
                        .goodsId(createOrderRequest.getGoodsId())
                        .orderTotal(createOrderRequest.getOrderTotal()).build());
        return CommonResponse.success(order.getId());
    }

    @RequestMapping(value="/orders", method= RequestMethod.GET)
    public CommonResponse<List<GetOrderResponse>> getAll() {
        return CommonResponse.success(StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(o->GetOrderResponse.builder().orderId(o.getId())
                        .orderState(o.getState())
                        .rejectionReason(o.getRejectionReason())
                        .build()).collect(Collectors.toList()));
    }

    @RequestMapping(value="/orders/{orderId}", method= RequestMethod.GET)
    public CommonResponse<GetOrderResponse> getOrder(@PathVariable Long orderId) {
        return orderRepository
                .findById(orderId)
                .map(o -> CommonResponse.success(GetOrderResponse.builder().orderId(o.getId())
                        .orderState(o.getState())
                        .rejectionReason(o.getRejectionReason())
                        .build()))
                .orElse(CommonResponse.failure(HttpStatus.NOT_FOUND,"order not found"));
    }

    @RequestMapping(value="/orders/customer/{customerId}", method= RequestMethod.GET)
    public CommonResponse<List<GetOrderResponse>> getOrdersByCustomerId(@PathVariable Long customerId) {
        return CommonResponse.success(orderRepository
                .findAllByOrderDetailsCustomerId(customerId)
                .stream()
                .map(o ->GetOrderResponse.builder().orderId(o.getId())
                        .orderState(o.getState())
                        .rejectionReason(o.getRejectionReason())
                        .build())
                .collect(Collectors.toList()));
    }
}
