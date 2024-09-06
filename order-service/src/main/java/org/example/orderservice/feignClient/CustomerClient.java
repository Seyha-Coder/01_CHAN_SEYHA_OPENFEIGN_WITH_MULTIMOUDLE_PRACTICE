package org.example.orderservice.feignClient;

import org.example.common.ApiResponse;
import org.example.customerservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service",
        url = "http://localhost:8081/api/v1/customer")
public interface CustomerClient {
    @GetMapping("/{id}")
    ApiResponse<Customer> findCustomerById(@PathVariable("id") Long id);
}
