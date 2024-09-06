package org.example.customerservice.controller;

import org.example.common.ApiResponse;
import org.example.customerservice.model.Customer;
import org.example.customerservice.model.CustomerRequest;
import org.example.customerservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping
    public ResponseEntity<ApiResponse> saveCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = customerService.saveCustomer(customerRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A customer has been created")
                .status(HttpStatus.CREATED)
                .code(201)
                .payload(customer)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping
    public ResponseEntity<ApiResponse> findAllCustomers() {
        List<Customer> customers = customerService.findAllCustomers();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("All customers has been received")
                .status(HttpStatus.OK)
                .code(200)
                .payload(customers)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findCustomerById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A customer has been received")
                .status(HttpStatus.OK)
                .code(200)
                .payload(customer)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        Customer customer = customerService.updateCustomer(id, customerRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A customer with id "+ id + " has been updated")
                .status(HttpStatus.OK)
                .code(200)
                .payload(customer)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A Customer with id "+ id + " has been deleted")
                .status(HttpStatus.OK)
                .code(200)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
