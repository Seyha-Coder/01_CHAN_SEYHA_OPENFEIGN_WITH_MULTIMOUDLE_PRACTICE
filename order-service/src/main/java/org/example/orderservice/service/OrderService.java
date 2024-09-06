package org.example.orderservice.service;

import org.example.orderservice.model.OrderRequest;
import org.example.orderservice.model.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getAllOrders();
    OrderResponse updateOrder(Long orderId, OrderRequest orderRequest);
    void deleteOrder(Long orderId);
}
