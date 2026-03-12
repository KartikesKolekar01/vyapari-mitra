package com.vyaparimitra.vyapari_mitra.controller;

import com.vyaparimitra.vyapari_mitra.model.Customer;
import com.vyaparimitra.vyapari_mitra.model.Transaction;
import com.vyaparimitra.vyapari_mitra.service.CustomerService;
import com.vyaparimitra.vyapari_mitra.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    // Global search
    @GetMapping
    public ResponseEntity<?> globalSearch(@RequestParam String keyword) {
        try {
            Map<String, Object> searchResults = new HashMap<>();

            // Search customers
            var customers = customerService.searchCustomers(keyword);
            searchResults.put("customers", customers);
            searchResults.put("customerCount", customers.size());

            // For each customer found, get their recent transactions
            Map<Long, Object> customerTransactions = new HashMap<>();
            for (Customer customer : customers) {
                var transactions = transactionService.getCustomerTransactions(customer.getId());
                if (!transactions.isEmpty()) {
                    customerTransactions.put(customer.getId(), transactions);
                }
            }
            searchResults.put("recentTransactions", customerTransactions);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", searchResults);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Quick search (just names and IDs)
    @GetMapping("/quick")
    public ResponseEntity<?> quickSearch(@RequestParam String keyword) {
        try {
            var customers = customerService.searchCustomers(keyword);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", customers);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}