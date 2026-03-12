package com.vyaparimitra.vyapari_mitra.dto;



import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CustomerDTO {

    private Long id;
    private String name;
    private String mobile;
    private String address;
    private String village;
    private BigDecimal totalCredit;
    private BigDecimal totalPaid;
    private BigDecimal balance;
    private LocalDateTime lastTransactionDate;
    private LocalDateTime createdAt;

    // Constructors
    public CustomerDTO() {}

    public CustomerDTO(Long id, String name, String mobile, String address,
                       String village, BigDecimal totalCredit, BigDecimal totalPaid,
                       BigDecimal balance, LocalDateTime lastTransactionDate,
                       LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.village = village;
        this.totalCredit = totalCredit;
        this.totalPaid = totalPaid;
        this.balance = balance;
        this.lastTransactionDate = lastTransactionDate;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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