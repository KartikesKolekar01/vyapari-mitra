package com.vyaparimitra.vyapari_mitra.service.impl;

import com.vyaparimitra.vyapari_mitra.dto.ReportDTO;
import com.vyaparimitra.vyapari_mitra.model.Customer;
import com.vyaparimitra.vyapari_mitra.model.Owner;
import com.vyaparimitra.vyapari_mitra.model.Transaction;
import com.vyaparimitra.vyapari_mitra.repository.CustomerRepository;
import com.vyaparimitra.vyapari_mitra.repository.TransactionRepository;
import com.vyaparimitra.vyapari_mitra.service.OwnerService;
import com.vyaparimitra.vyapari_mitra.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerService ownerService;  // ✅ OwnerService inject करा

    @Override
    public ReportDTO getDailyReport(LocalDate date) {

        Owner currentOwner = ownerService.getCurrentOwner();  // ✅ Current owner मिळवा

        ReportDTO report = new ReportDTO();
        report.setReportType("DAILY");
        report.setPeriod(date.toString());

        // ✅ Owner ID सह transactions मिळवा
        List<Transaction> todayTransactions =
                transactionRepository.findByOwnerIdAndTransactionDate(currentOwner.getId(), date);

        BigDecimal totalCredit = BigDecimal.ZERO;
        BigDecimal totalPayment = BigDecimal.ZERO;

        for (Transaction t : todayTransactions) {
            if (t.getType().equals("CREDIT")) {
                totalCredit = totalCredit.add(t.getAmount());
            } else {
                totalPayment = totalPayment.add(t.getAmount());
            }
        }

        report.setTotalCredit(totalCredit);
        report.setTotalPayment(totalPayment);
        report.setPendingAmount(totalCredit.subtract(totalPayment));

        // ✅ Owner साठी customer count
        report.setCustomerCount((int) customerRepository.countByOwnerId(currentOwner.getId()));

        // ✅ Owner साठी top defaulters
        report.setTopDefaulters(getTopDefaulters(currentOwner.getId()));

        return report;
    }

    @Override
    public ReportDTO getMonthlyReport(int year, int month) {

        Owner currentOwner = ownerService.getCurrentOwner();

        ReportDTO report = new ReportDTO();
        report.setReportType("MONTHLY");

        YearMonth yearMonth = YearMonth.of(year, month);
        String monthName = getMonthName(month);
        report.setPeriod(monthName + " " + year);

        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // ✅ Owner ID सह transactions मिळवा
        List<Transaction> monthlyTransactions =
                transactionRepository.findByOwnerIdAndTransactionDateBetween(currentOwner.getId(), startDate, endDate);

        BigDecimal totalCredit = BigDecimal.ZERO;
        BigDecimal totalPayment = BigDecimal.ZERO;

        for (Transaction t : monthlyTransactions) {
            if (t.getType().equals("CREDIT")) {
                totalCredit = totalCredit.add(t.getAmount());
            } else {
                totalPayment = totalPayment.add(t.getAmount());
            }
        }

        report.setTotalCredit(totalCredit);
        report.setTotalPayment(totalPayment);
        report.setPendingAmount(totalCredit.subtract(totalPayment));

        // Daily data for chart
        Map<String, BigDecimal> dailyData = new LinkedHashMap<>();
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);

            // ✅ Owner ID सह daily transactions
            List<Transaction> dayTransactions =
                    transactionRepository.findByOwnerIdAndTransactionDate(currentOwner.getId(), currentDate);

            BigDecimal dayTotal = BigDecimal.ZERO;
            for (Transaction t : dayTransactions) {
                dayTotal = dayTotal.add(t.getAmount());
            }

            if (dayTotal.compareTo(BigDecimal.ZERO) > 0) {
                dailyData.put(String.valueOf(day), dayTotal);
            }
        }
        report.setDailyData(dailyData);

        report.setTopDefaulters(getTopDefaulters(currentOwner.getId()));

        return report;
    }

    @Override
    public ReportDTO getYearlyReport(int year) {

        Owner currentOwner = ownerService.getCurrentOwner();

        ReportDTO report = new ReportDTO();
        report.setReportType("YEARLY");
        report.setPeriod(String.valueOf(year));

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        // ✅ Owner ID सह transactions मिळवा
        List<Transaction> yearlyTransactions =
                transactionRepository.findByOwnerIdAndTransactionDateBetween(currentOwner.getId(), startDate, endDate);

        BigDecimal totalCredit = BigDecimal.ZERO;
        BigDecimal totalPayment = BigDecimal.ZERO;

        for (Transaction t : yearlyTransactions) {
            if (t.getType().equals("CREDIT")) {
                totalCredit = totalCredit.add(t.getAmount());
            } else {
                totalPayment = totalPayment.add(t.getAmount());
            }
        }

        report.setTotalCredit(totalCredit);
        report.setTotalPayment(totalPayment);
        report.setPendingAmount(totalCredit.subtract(totalPayment));

        // Monthly data for chart
        Map<String, BigDecimal> monthlyData = new LinkedHashMap<>();
        String[] monthNames = {"जाने", "फेब्रु", "मार्च", "एप्रिल", "मे", "जून",
                "जुलै", "ऑगस्ट", "सप्टें", "ऑक्टो", "नोव्हें", "डिसें"};

        for (int month = 1; month <= 12; month++) {
            LocalDate monthStart = LocalDate.of(year, month, 1);
            LocalDate monthEnd = YearMonth.of(year, month).atEndOfMonth();

            // ✅ Owner ID सह monthly transactions
            List<Transaction> monthTransactions =
                    transactionRepository.findByOwnerIdAndTransactionDateBetween(currentOwner.getId(), monthStart, monthEnd);

            BigDecimal monthTotal = BigDecimal.ZERO;
            for (Transaction t : monthTransactions) {
                monthTotal = monthTotal.add(t.getAmount());
            }

            if (monthTotal.compareTo(BigDecimal.ZERO) > 0) {
                monthlyData.put(monthNames[month-1], monthTotal);
            }
        }
        report.setDailyData(monthlyData);

        report.setTopDefaulters(getTopDefaulters(currentOwner.getId()));

        return report;
    }

    @Override
    public ReportDTO getPendingPaymentsReport() {

        Owner currentOwner = ownerService.getCurrentOwner();

        ReportDTO report = new ReportDTO();
        report.setReportType("PENDING");
        report.setPeriod("थकबाकी अहवाल");

        // ✅ Owner ID सह pending payments
        List<Transaction> pendingTransactions =
                transactionRepository.findPendingPayments(currentOwner.getId(), LocalDate.now());

        BigDecimal totalPending = BigDecimal.ZERO;
        for (Transaction t : pendingTransactions) {
            totalPending = totalPending.add(t.getAmount());
        }

        report.setPendingAmount(totalPending);

        // Group by customer
        Map<String, BigDecimal> customerWise = new HashMap<>();
        for (Transaction t : pendingTransactions) {
            String customerName = t.getCustomer().getName();
            BigDecimal amount = customerWise.getOrDefault(customerName, BigDecimal.ZERO);
            customerWise.put(customerName, amount.add(t.getAmount()));
        }
        report.setCategoryData(customerWise);

        return report;
    }

    @Override
    public ReportDTO getCustomerReport(Long customerId) {

        Owner currentOwner = ownerService.getCurrentOwner();

        ReportDTO report = new ReportDTO();
        report.setReportType("CUSTOMER");

        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("ग्राहक सापडला नाही! ID: " + customerId);
        }

        Customer customer = customerOpt.get();

        // ✅ Verify customer belongs to current owner
        if (!customer.getOwner().getId().equals(currentOwner.getId())) {
            throw new RuntimeException("तुम्हाला या ग्राहकाचा अहवाल पाहण्याची परवानगी नाही!");
        }

        report.setPeriod(customer.getName());
        report.setTotalCredit(customer.getTotalCredit());
        report.setTotalPayment(customer.getTotalPaid());
        report.setPendingAmount(customer.getBalance());

        return report;
    }

    // Helper method to get top defaulters for specific owner
    private List<Map<String, Object>> getTopDefaulters(Long ownerId) {

        List<Customer> customersWithBalance =
                customerRepository.findByOwnerIdAndBalanceGreaterThan(ownerId, BigDecimal.ZERO);
        List<Map<String, Object>> topDefaulters = new ArrayList<>();

        // Sort by balance descending (highest first)
        customersWithBalance.sort((c1, c2) -> c2.getBalance().compareTo(c1.getBalance()));

        // Take top 5
        int count = Math.min(5, customersWithBalance.size());
        for (int i = 0; i < count; i++) {
            Customer c = customersWithBalance.get(i);
            Map<String, Object> defaulter = new HashMap<>();
            defaulter.put("name", c.getName());
            defaulter.put("mobile", c.getMobile());
            defaulter.put("balance", c.getBalance());
            defaulter.put("village", c.getVillage());
            topDefaulters.add(defaulter);
        }

        return topDefaulters;
    }

    // Helper method to get month name in Marathi
    private String getMonthName(int month) {
        String[] marathiMonths = {
                "जानेवारी", "फेब्रुवारी", "मार्च", "एप्रिल", "मे", "जून",
                "जुलै", "ऑगस्ट", "सप्टेंबर", "ऑक्टोबर", "नोव्हेंबर", "डिसेंबर"
        };
        return marathiMonths[month - 1];
    }
}