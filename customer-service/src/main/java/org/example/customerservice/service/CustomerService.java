package org.example.customerservice.service;

import org.example.customerservice.model.Customer;
import org.example.customerservice.model.CustomerRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer saveCustomer(CustomerRequest customerRequest);
    List<Customer> findAllCustomers();
    Optional<Customer> findCustomerById(Long id);
    Customer updateCustomer(Long id, CustomerRequest customerRequest);
    void deleteCustomer(Long id);
}
