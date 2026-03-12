package com.vyaparimitra.vyapari_mitra.controller;

import com.vyaparimitra.vyapari_mitra.dto.CustomerDTO;
import com.vyaparimitra.vyapari_mitra.model.Customer;
import com.vyaparimitra.vyapari_mitra.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Create new customer
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            Customer customer = customerService.createCustomer(customerDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "ग्राहक यशस्वीरित्या जोडला गेला! ✅");
            response.put("data", customer);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all customers
    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", customers.size());
            response.put("data", customers);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        try {
            Customer customer = customerService.getCustomerById(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", customer);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    // Update customer
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        try {
            Customer customer = customerService.updateCustomer(id, customerDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "ग्राहक माहिती यशस्वीरित्या अद्यावत केली! ✏️");
            response.put("data", customer);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete customer
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "ग्राहक यशस्वीरित्या हटवला गेला! 🗑️");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Search customers
    @GetMapping("/search")
    public ResponseEntity<?> searchCustomers(@RequestParam String keyword) {
        try {
            List<Customer> customers = customerService.searchCustomers(keyword);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", customers.size());
            response.put("data", customers);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get customers with balance (थकबाकीदार)
    @GetMapping("/balance")
    public ResponseEntity<?> getCustomersWithBalance() {
        try {
            List<Customer> customers = customerService.getCustomersWithBalance();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", customers.size());
            response.put("data", customers);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get customers by village
    @GetMapping("/village/{village}")
    public ResponseEntity<?> getCustomersByVillage(@PathVariable String village) {
        try {
            List<Customer> customers = customerService.getCustomersByVillage(village);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", customers.size());
            response.put("data", customers);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get customer count
    @GetMapping("/count")
    public ResponseEntity<?> getCustomerCount() {
        try {
            long count = customerService.getCustomerCount();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", count);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}