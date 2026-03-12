package com.vyaparimitra.vyapari_mitra.service;


import com.vyaparimitra.vyapari_mitra.dto.ReportDTO;
import java.time.LocalDate;

public interface ReportService {

    // Get daily report
    ReportDTO getDailyReport(LocalDate date);

    // Get monthly report
    ReportDTO getMonthlyReport(int year, int month);

    // Get yearly report
    ReportDTO getYearlyReport(int year);

    // Get pending payments report
    ReportDTO getPendingPaymentsReport();

    // Get customer wise report
    ReportDTO getCustomerReport(Long customerId);
}