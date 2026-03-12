package com.vyaparimitra.vyapari_mitra.controller;

import com.vyaparimitra.vyapari_mitra.dto.TransactionDTO;
import com.vyaparimitra.vyapari_mitra.model.Transaction;
import com.vyaparimitra.vyapari_mitra.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Add new credit (उधारी)
    @PostMapping("/credit")
    public ResponseEntity<?> addCredit(@RequestBody TransactionDTO transactionDTO) {
        try {
            Transaction transaction = transactionService.addCredit(transactionDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "उधारी यशस्वीरित्या नोंदली! 📝");
            response.put("data", transaction);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Add new payment (पैसे भरले)
    @PostMapping("/payment")
    public ResponseEntity<?> addPayment(@RequestBody TransactionDTO transactionDTO) {
        try {
            Transaction transaction = transactionService.addPayment(transactionDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "पैसे यशस्वीरित्या नोंदले! 💰");
            response.put("data", transaction);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Get customer transactions
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCustomerTransactions(@PathVariable Long customerId) {
        try {
            List<Transaction> transactions = transactionService.getCustomerTransactions(customerId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", transactions.size());
            response.put("data", transactions);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        try {
            Transaction transaction = transactionService.getTransactionById(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", transaction);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    // Get today's transactions
    @GetMapping("/today")
    public ResponseEntity<?> getTodayTransactions() {
        try {
            List<Transaction> transactions = transactionService.getTodayTransactions();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", transactions.size());
            response.put("data", transactions);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get pending payments (थकबाकी)
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingPayments() {
        try {
            List<Transaction> transactions = transactionService.getPendingPayments();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", transactions.size());
            response.put("data", transactions);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get transactions between dates
    @GetMapping("/between")
    public ResponseEntity<?> getTransactionsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsBetweenDates(start, end);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", transactions.size());
            response.put("data", transactions);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Get transactions by type
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getTransactionsByType(@PathVariable String type) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByType(type);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", transactions.size());
            response.put("data", transactions);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Get customer balance
    @GetMapping("/balance/{customerId}")
    public ResponseEntity<?> getCustomerBalance(@PathVariable Long customerId) {
        try {
            BigDecimal balance = transactionService.getCustomerBalance(customerId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("balance", balance);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    // Delete transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteTransaction(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "व्यवहार यशस्वीरित्या हटवला! 🗑️");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}