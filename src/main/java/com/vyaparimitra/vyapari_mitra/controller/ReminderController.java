package com.vyaparimitra.vyapari_mitra.controller;

import com.vyaparimitra.vyapari_mitra.model.Customer;
import com.vyaparimitra.vyapari_mitra.model.Transaction;
import com.vyaparimitra.vyapari_mitra.service.CustomerService;
import com.vyaparimitra.vyapari_mitra.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin(origins = "*")
public class ReminderController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    // Today's reminders
    @GetMapping("/today")
    public ResponseEntity<?> getTodayReminders() {
        try {
            List<Map<String, Object>> reminders = new ArrayList<>();

            // Get pending payments
            var pendingTransactions = transactionService.getPendingPayments();

            for (Transaction t : pendingTransactions) {
                if (t.getDueDate() != null) {
                    Map<String, Object> reminder = new HashMap<>();
                    reminder.put("customerId", t.getCustomer().getId());
                    reminder.put("customerName", t.getCustomer().getName());
                    reminder.put("amount", t.getAmount());
                    reminder.put("dueDate", t.getDueDate());
                    reminder.put("type", "PENDING");

                    long daysOverdue = LocalDate.now().toEpochDay() - t.getDueDate().toEpochDay();
                    reminder.put("daysOverdue", daysOverdue);

                    reminders.add(reminder);
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", reminders.size());
            response.put("data", reminders);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Due this week
    @GetMapping("/week")
    public ResponseEntity<?> getThisWeekReminders() {
        try {
            List<Map<String, Object>> reminders = new ArrayList<>();

            LocalDate today = LocalDate.now();
            LocalDate weekLater = today.plusDays(7);

            var customers = customerService.getCustomersWithBalance();

            for (Customer customer : customers) {
                var transactions = transactionService.getCustomerTransactions(customer.getId());

                for (Transaction t : transactions) {
                    if (t.getType().equals("CREDIT") && t.getDueDate() != null) {
                        if (!t.getDueDate().isBefore(today) && !t.getDueDate().isAfter(weekLater)) {
                            Map<String, Object> reminder = new HashMap<>();
                            reminder.put("customerId", customer.getId());
                            reminder.put("customerName", customer.getName());
                            reminder.put("amount", t.getAmount());
                            reminder.put("dueDate", t.getDueDate());
                            reminder.put("type", "UPCOMING");
                            reminders.add(reminder);
                            break;
                        }
                    }
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", reminders.size());
            response.put("data", reminders);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}