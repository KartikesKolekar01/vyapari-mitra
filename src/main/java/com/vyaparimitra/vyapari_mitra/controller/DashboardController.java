package com.vyaparimitra.vyapari_mitra.controller;

import com.vyaparimitra.vyapari_mitra.service.CustomerService;
import com.vyaparimitra.vyapari_mitra.service.TransactionService;
import com.vyaparimitra.vyapari_mitra.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private OwnerService ownerService;

    // Home dashboard data
    @GetMapping("/home")
    public ResponseEntity<?> getDashboardData() {
        try {
            Map<String, Object> dashboardData = new HashMap<>();

            // Customer stats
            long totalCustomers = customerService.getCustomerCount();
            int customersWithBalance = customerService.getCustomersWithBalance().size();

            // Transaction stats - today
            int todayTransactions = transactionService.getTodayTransactions().size();

            // Pending payments
            int pendingPayments = transactionService.getPendingPayments().size();

            // Calculate total balance (all customers)
            BigDecimal totalBalance = BigDecimal.ZERO;
            for (var customer : customerService.getCustomersWithBalance()) {
                totalBalance = totalBalance.add(customer.getBalance());
            }

            // Owner info
            var owner = ownerService.getOwnerDetails();

            dashboardData.put("totalCustomers", totalCustomers);
            dashboardData.put("customersWithBalance", customersWithBalance);
            dashboardData.put("todayTransactions", todayTransactions);
            dashboardData.put("pendingPayments", pendingPayments);
            dashboardData.put("totalOutstanding", totalBalance);
            dashboardData.put("shopName", owner.getShopName());
            dashboardData.put("ownerName", owner.getOwnerName());
            dashboardData.put("date", LocalDate.now().toString());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", dashboardData);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Quick stats
    @GetMapping("/stats")
    public ResponseEntity<?> getQuickStats() {
        try {
            Map<String, Object> stats = new HashMap<>();

            stats.put("totalCustomers", customerService.getCustomerCount());
            stats.put("todayCredits", transactionService.getTodayTransactions().stream()
                    .filter(t -> t.getType().equals("CREDIT")).count());
            stats.put("todayPayments", transactionService.getTodayTransactions().stream()
                    .filter(t -> t.getType().equals("PAYMENT")).count());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", stats);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}