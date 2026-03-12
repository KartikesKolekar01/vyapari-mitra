package com.vyaparimitra.vyapari_mitra.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ReportDTO {

    private String reportType;        // "DAILY", "MONTHLY", "YEARLY", "PENDING", "CUSTOMER"
    private String period;             // "मार्च २०२६", "जानेवारी २०२६", etc.

    // Summary
    private BigDecimal totalCredit;    // एकूण उधारी
    private BigDecimal totalPayment;   // एकूण वसूली
    private BigDecimal pendingAmount;  // थकबाकी
    private int customerCount;         // एकूण ग्राहक

    // Charts data
    private Map<String, BigDecimal> dailyData;     // दिवसनिहाय आकडे
    private Map<String, BigDecimal> categoryData;  // श्रेणीनिहाय (गावनिहाय, ग्राहकनिहाय)

    // Top defaulters
    private List<Map<String, Object>> topDefaulters;

    // Constructors
    public ReportDTO() {}

    public ReportDTO(String reportType, String period, BigDecimal totalCredit,
                     BigDecimal totalPayment, BigDecimal pendingAmount, int customerCount,
                     Map<String, BigDecimal> dailyData, Map<String, BigDecimal> categoryData,
                     List<Map<String, Object>> topDefaulters) {
        this.reportType = reportType;
        this.period = period;
        this.totalCredit = totalCredit;
        this.totalPayment = totalPayment;
        this.pendingAmount = pendingAmount;
        this.customerCount = customerCount;
        this.dailyData = dailyData;
        this.categoryData = categoryData;
        this.topDefaulters = topDefaulters;
    }

    // Getters and Setters
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(BigDecimal totalCredit) {
        this.totalCredit = totalCredit;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public BigDecimal getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(BigDecimal pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public Map<String, BigDecimal> getDailyData() {
        return dailyData;
    }

    public void setDailyData(Map<String, BigDecimal> dailyData) {
        this.dailyData = dailyData;
    }

    public Map<String, BigDecimal> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(Map<String, BigDecimal> categoryData) {
        this.categoryData = categoryData;
    }

    public List<Map<String, Object>> getTopDefaulters() {
        return topDefaulters;
    }

    public void setTopDefaulters(List<Map<String, Object>> topDefaulters) {
        this.topDefaulters = topDefaulters;
    }
}