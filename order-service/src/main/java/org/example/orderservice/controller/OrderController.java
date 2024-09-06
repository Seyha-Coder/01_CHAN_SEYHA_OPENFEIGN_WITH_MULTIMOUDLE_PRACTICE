package org.example.orderservice.controller;

import org.example.common.ApiResponse;
import org.example.orderservice.model.OrderRequest;
import org.example.orderservice.model.OrderResponse;
import org.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    public ResponseEntity<ApiResponse> saveOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Order created successfully")
                .status(HttpStatus.CREATED)
                .code(201)
                .payload(orderResponse)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findOrderById(@PathVariable Long id){
        OrderResponse orderResponse = orderService.getOrderById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("An order has retrieve successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(orderResponse)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ApiResponse> finAllOrder(){
        List<OrderResponse> orderResponseList = orderService.getAllOrders();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("All orders has retrieve successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(orderResponseList)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.updateOrder(id, orderRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("An order has updated successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(orderResponse)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("An order has delete successfully")
                .status(HttpStatus.OK)
                .code(200)
                .payload(null)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
