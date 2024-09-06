package org.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.customerservice.model.Customer;
import org.example.productservice.model.Product;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private Customer customer;
    private List<Product> products;
    private LocalDate orderDate;

    public OrderResponse(Order order, Customer customer, List<Product> products) {
        this.id=order.getId();
        this.customer=customer;
        this.products=products;
        this.orderDate=order.getOrderDate();
    }
}
