package com.vyaparimitra.vyapari_mitra.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Owner relationship - हे फील्ड add करा
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    private String name;
    private String mobile;
    private String address;
    private String village;

    @Column(name = "total_credit")
    private BigDecimal totalCredit;

    @Column(name = "total_paid")
    private BigDecimal totalPaid;

    private BigDecimal balance;

    @Column(name = "last_transaction_date")
    private LocalDateTime lastTransactionDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public Customer() {
        this.totalCredit = BigDecimal.ZERO;
        this.totalPaid = BigDecimal.ZERO;
        this.balance = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }

    public Customer(String name, String mobile, String address, String village) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.village = village;
        this.totalCredit = BigDecimal.ZERO;
        this.totalPaid = BigDecimal.ZERO;
        this.balance = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ✅ Owner Getter/Setter - या methods add करा
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(BigDecimal totalCredit) {
        this.totalCredit = totalCredit;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(LocalDateTime lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}