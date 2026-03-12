package com.vyaparimitra.vyapari_mitra.controller;

import com.vyaparimitra.vyapari_mitra.dto.ReportDTO;
import com.vyaparimitra.vyapari_mitra.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Daily report
    @GetMapping("/daily")
    public ResponseEntity<?> getDailyReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            if (date == null) {
                date = LocalDate.now();
            }

            ReportDTO report = reportService.getDailyReport(date);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", report);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Monthly report
    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyReport(
            @RequestParam int year,
            @RequestParam int month) {
        try {
            ReportDTO report = reportService.getMonthlyReport(year, month);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", report);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Yearly report
    @GetMapping("/yearly")
    public ResponseEntity<?> getYearlyReport(@RequestParam int year) {
        try {
            ReportDTO report = reportService.getYearlyReport(year);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", report);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Pending payments report
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingPaymentsReport() {
        try {
            ReportDTO report = reportService.getPendingPaymentsReport();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", report);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Customer report
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCustomerReport(@PathVariable Long customerId) {
        try {
            ReportDTO report = reportService.getCustomerReport(customerId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", report);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}