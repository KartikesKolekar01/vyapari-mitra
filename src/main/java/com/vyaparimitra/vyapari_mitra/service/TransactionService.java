package com.vyaparimitra.vyapari_mitra.service;

import com.vyaparimitra.vyapari_mitra.dto.TransactionDTO;
import com.vyaparimitra.vyapari_mitra.model.Transaction;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;  // ही import missing होती

public interface TransactionService {

    // Add new credit (उधारी)
    Transaction addCredit(TransactionDTO transactionDTO);

    // Add new payment (पैसे भरले)
    Transaction addPayment(TransactionDTO transactionDTO);

    // Get all transactions for a customer
    List<Transaction> getCustomerTransactions(Long customerId);

    // Get transaction by ID
    Transaction getTransactionById(Long id);

    // Get today's transactions
    List<Transaction> getTodayTransactions();

    // Get pending payments (थकबाकी)
    List<Transaction> getPendingPayments();

    // Get transactions between dates
    List<Transaction> getTransactionsBetweenDates(LocalDate startDate, LocalDate endDate);

    // Get transactions by type
    List<Transaction> getTransactionsByType(String type);

    // Update transaction photo
    Transaction updateTransactionPhoto(Long id, String photoPath);

    // Delete transaction
    void deleteTransaction(Long id);

    // Get customer balance
    BigDecimal getCustomerBalance(Long customerId);  // line 42
}