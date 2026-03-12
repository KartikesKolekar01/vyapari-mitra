package com.vyaparimitra.vyapari_mitra.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private Long customerId;
    private String customerName;
    private String type;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private LocalDate dueDate;
    private String description;
    private String photoPath;
    private LocalDateTime createdAt;

    // Constructors
    public TransactionDTO() {}

    public TransactionDTO(Long id, Long customerId, String customerName, String type,
                          BigDecimal amount, LocalDate transactionDate, LocalDate dueDate,
                          String description, String photoPath, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
        this.description = description;
        this.photoPath = photoPath;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}  // <-- फक्त एकच closing brace असावा