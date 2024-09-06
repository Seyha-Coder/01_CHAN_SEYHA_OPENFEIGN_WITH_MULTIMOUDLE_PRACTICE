package org.example.customerservice.service;

import org.example.common.exception.CustomNotfoundException;
import org.example.customerservice.model.Customer;
import org.example.customerservice.model.CustomerRequest;
import org.example.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(id).orElseThrow(
                () -> new CustomNotfoundException("Customer with id " + id + " not found")
        ));
        return customer;
    }

    @Override
    public Customer updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomNotfoundException("Customer with id "+id+ " not found")
        );
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomNotfoundException("Customer with id "+id+ " not found")
        );
        customerRepository.delete(customer);
    }
}
