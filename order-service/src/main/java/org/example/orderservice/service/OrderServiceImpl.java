package org.example.orderservice.service;

import org.example.common.ApiResponse;
import org.example.common.exception.CustomNotfoundException;
import org.example.customerservice.model.Customer;
import org.example.orderservice.feignClient.CustomerClient;
import org.example.orderservice.feignClient.ProductClient;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.OrderRequest;
import org.example.orderservice.model.OrderResponse;
import org.example.orderservice.repository.OrderRepository;
import org.example.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerClient customerClient, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.customerClient = customerClient;
        this.productClient = productClient;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        ApiResponse<Customer> customerResponse = customerClient.findCustomerById(orderRequest.getCustomerId());
        if (customerResponse == null || customerResponse.getPayload() == null) {
            throw new CustomNotfoundException("Customer not found with id " + orderRequest.getCustomerId());
        }
        Customer customer = customerResponse.getPayload();

        List<Product> products = orderRequest.getProductIds().stream()
                .map(productId -> {
                    ApiResponse<Product> productResponse = productClient.findProductById(productId);
                    if (productResponse == null || productResponse.getPayload() == null) {
                        throw new CustomNotfoundException("Product not found with id " + productId);
                    }
                    return productResponse.getPayload();
                })
                .collect(Collectors.toList());

        Order order = new Order();
        order.setOrderDate(orderRequest.getOrderDate());
        order.setCustomerId(customer.getId());

        List<Long> productIds = products.stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        order.setProductIds(productIds);

        orderRepository.save(order);
        return new OrderResponse(order, customer, products);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new CustomNotfoundException("Order not found with id " + orderId)
        );
        Customer customer = customerClient.findCustomerById(order.getCustomerId()).getPayload();
        List<Product> products = new ArrayList<>();
        for (Long productId : order.getProductIds()) {
            Product product = productClient.findProductById(productId).getPayload();
            products.add(product);
        }
        return new OrderResponse(order, customer, products);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Order order : orders) {
            Customer customer = customerClient.findCustomerById(order.getCustomerId()).getPayload();
            List<Product> products = order.getProductIds().stream()
                    .map(productId -> productClient.findProductById(productId).getPayload())
                    .collect(Collectors.toList());
            OrderResponse orderResponse = new OrderResponse(order, customer, products);
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }


    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new CustomNotfoundException("Order not found with id " + orderId)
        );
        Customer customer = customerClient.findCustomerById(orderRequest.getCustomerId()).getPayload();
        List<Product> products = new ArrayList<>();
        for(Long productId : orderRequest.getProductIds()) {
            Product product = productClient.findProductById(productId).getPayload();
            products.add(product);
        }
        order.setCustomerId(customer.getId());
        order.setOrderDate(orderRequest.getOrderDate());
        List<Long> productIds = products.stream().map(Product::getId).collect(Collectors.toList());
        order.setProductIds(productIds);
        orderRepository.save(order);
        return new OrderResponse(order, customer, products);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new CustomNotfoundException("Order not found with id " + orderId)
        );
        orderRepository.delete(order);
    }
}
