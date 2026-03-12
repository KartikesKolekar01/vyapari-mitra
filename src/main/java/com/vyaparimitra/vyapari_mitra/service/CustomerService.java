package com.vyaparimitra.vyapari_mitra.service;


import com.vyaparimitra.vyapari_mitra.dto.CustomerDTO;
import com.vyaparimitra.vyapari_mitra.model.Customer;
import java.util.List;

public interface CustomerService {

    // Create new customer
    Customer createCustomer(CustomerDTO customerDTO);

    // Get all customers
    List<Customer> getAllCustomers();

    // Get customer by ID
    Customer getCustomerById(Long id);

    // Update customer
    Customer updateCustomer(Long id, CustomerDTO customerDTO);

    // Delete customer
    void deleteCustomer(Long id);

    // Search customers by name or mobile
    List<Customer> searchCustomers(String keyword);

    // Get customers with balance (थकबाकीदार)
    List<Customer> getCustomersWithBalance();

    // Get customers by village
    List<Customer> getCustomersByVillage(String village);

    // Get total count of customers
    long getCustomerCount();
}