package com.vyaparimitra.vyapari_mitra.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // ✅ Owner relationship - हे फील्ड add करा
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    private String type;  // "CREDIT" or "PAYMENT"
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    private String description;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public Transaction() {
        this.createdAt = LocalDateTime.now();
    }

    public Transaction(Customer customer, Owner owner, String type, BigDecimal amount,
                       LocalDate transactionDate, LocalDate dueDate, String description) {
        this.customer = customer;
        this.owner = owner;  // ✅ Owner set
        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // ✅ Owner Getter/Setter - या methods add करा
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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
}