package com.vyaparimitra.vyapari_mitra.service.impl;

import com.vyaparimitra.vyapari_mitra.dto.TransactionDTO;
import com.vyaparimitra.vyapari_mitra.model.Customer;
import com.vyaparimitra.vyapari_mitra.model.Owner;
import com.vyaparimitra.vyapari_mitra.model.Transaction;
import com.vyaparimitra.vyapari_mitra.repository.CustomerRepository;
import com.vyaparimitra.vyapari_mitra.repository.TransactionRepository;
import com.vyaparimitra.vyapari_mitra.service.OwnerService;
import com.vyaparimitra.vyapari_mitra.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerService ownerService;

    @Override
    @Transactional
    public Transaction addCredit(TransactionDTO transactionDTO) {

        // Get current logged in owner
        Owner currentOwner = ownerService.getCurrentOwner();

        // Validate customer
        if (transactionDTO.getCustomerId() == null) {
            throw new RuntimeException("ग्राहक ID आवश्यक आहे!");
        }

        // Get customer
        Customer customer = customerRepository.findById(transactionDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("ग्राहक सापडला नाही! ID: " +
                        transactionDTO.getCustomerId()));

        // Verify customer belongs to current owner
        if (!customer.getOwner().getId().equals(currentOwner.getId())) {
            throw new RuntimeException("तुम्हाला या ग्राहकासाठी व्यवहार करण्याची परवानगी नाही!");
        }

        // Validate amount
        if (transactionDTO.getAmount() == null) {
            throw new RuntimeException("रक्कम आवश्यक आहे!");
        }

        if (transactionDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("रक्कम ० पेक्षा जास्त असणे आवश्यक आहे!");
        }

        // Set transaction date if not provided
        LocalDate transactionDate = transactionDTO.getTransactionDate();
        if (transactionDate == null) {
            transactionDate = LocalDate.now();
        }

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setOwner(currentOwner);  // ✅ Important: Set owner
        transaction.setType("CREDIT");
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(transactionDate);
        transaction.setDueDate(transactionDTO.getDueDate());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setPhotoPath(transactionDTO.getPhotoPath());
        transaction.setCreatedAt(LocalDateTime.now());

        // Update customer balance
        BigDecimal newTotalCredit = customer.getTotalCredit().add(transactionDTO.getAmount());
        BigDecimal newBalance = customer.getBalance().add(transactionDTO.getAmount());

        customer.setTotalCredit(newTotalCredit);
        customer.setBalance(newBalance);
        customer.setLastTransactionDate(LocalDateTime.now());

        // Save both
        customerRepository.save(customer);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return savedTransaction;
    }

    @Override
    @Transactional
    public Transaction addPayment(TransactionDTO transactionDTO) {

        // Get current logged in owner
        Owner currentOwner = ownerService.getCurrentOwner();

        // Validate customer
        if (transactionDTO.getCustomerId() == null) {
            throw new RuntimeException("ग्राहक ID आवश्यक आहे!");
        }

        // Get customer
        Customer customer = customerRepository.findById(transactionDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("ग्राहक सापडला नाही! ID: " +
                        transactionDTO.getCustomerId()));

        // Verify customer belongs to current owner
        if (!customer.getOwner().getId().equals(currentOwner.getId())) {
            throw new RuntimeException("तुम्हाला या ग्राहकासाठी व्यवहार करण्याची परवानगी नाही!");
        }

        // Validate amount
        if (transactionDTO.getAmount() == null) {
            throw new RuntimeException("रक्कम आवश्यक आहे!");
        }

        if (transactionDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("रक्कम ० पेक्षा जास्त असणे आवश्यक आहे!");
        }

        // Check if payment amount is not greater than balance
        if (transactionDTO.getAmount().compareTo(customer.getBalance()) > 0) {
            throw new RuntimeException("भरलेली रक्कम उधारीपेक्षा जास्त असू शकत नाही! " +
                    "बाकी: ₹" + customer.getBalance());
        }

        // Set transaction date if not provided
        LocalDate transactionDate = transactionDTO.getTransactionDate();
        if (transactionDate == null) {
            transactionDate = LocalDate.now();
        }

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setOwner(currentOwner);  // ✅ Important: Set owner
        transaction.setType("PAYMENT");
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(transactionDate);
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setPhotoPath(transactionDTO.getPhotoPath());
        transaction.setCreatedAt(LocalDateTime.now());

        // Update customer balance
        BigDecimal newTotalPaid = customer.getTotalPaid().add(transactionDTO.getAmount());
        BigDecimal newBalance = customer.getBalance().subtract(transactionDTO.getAmount());

        customer.setTotalPaid(newTotalPaid);
        customer.setBalance(newBalance);
        customer.setLastTransactionDate(LocalDateTime.now());

        // Save both
        customerRepository.save(customer);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return savedTransaction;
    }

    @Override
    public List<Transaction> getCustomerTransactions(Long customerId) {

        Owner currentOwner = ownerService.getCurrentOwner();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("ग्राहक सापडला नाही! ID: " + customerId));

        // Verify customer belongs to current owner
        if (!customer.getOwner().getId().equals(currentOwner.getId())) {
            throw new RuntimeException("तुम्हाला या ग्राहकाचे व्यवहार पाहण्याची परवानगी नाही!");
        }

        return transactionRepository.findByCustomerOrderByTransactionDateDesc(customer);
    }

    @Override
    public Transaction getTransactionById(Long id) {

        Owner currentOwner = ownerService.getCurrentOwner();

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("व्यवहार सापडला नाही! ID: " + id));

        // Verify transaction belongs to current owner
        if (!transaction.getOwner().getId().equals(currentOwner.getId())) {
            throw new RuntimeException("तुम्हाला हा व्यवहार पाहण्याची परवानगी नाही!");
        }

        return transaction;
    }

    @Override
    public List<Transaction> getTodayTransactions() {

        Owner currentOwner = ownerService.getCurrentOwner();
        return transactionRepository.findByOwnerIdAndTransactionDate(currentOwner.getId(), LocalDate.now());
    }

    @Override
    public List<Transaction> getPendingPayments() {

        Owner currentOwner = ownerService.getCurrentOwner();
        return transactionRepository.findPendingPayments(currentOwner.getId(), LocalDate.now());
    }

    @Override
    public List<Transaction> getTransactionsBetweenDates(LocalDate startDate, LocalDate endDate) {

        Owner currentOwner = ownerService.getCurrentOwner();

        if (startDate == null || endDate == null) {
            throw new RuntimeException("सुरुवात आणि शेवटची तारीख आवश्यक आहे!");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("सुरुवातीची तारीख शेवटच्या तारखेपेक्षा नंतरची असू शकत नाही!");
        }

        return transactionRepository.findByOwnerIdAndTransactionDateBetween(currentOwner.getId(), startDate, endDate);
    }

    @Override
    public List<Transaction> getTransactionsByType(String type) {

        Owner currentOwner = ownerService.getCurrentOwner();

        if (type == null || (!type.equals("CREDIT") && !type.equals("PAYMENT"))) {
            throw new RuntimeException("प्रकार CREDIT किंवा PAYMENT असावा!");
        }

        return transactionRepository.findByOwnerIdAndTypeOrderByTransactionDateDesc(currentOwner.getId(), type);
    }

    @Override
    @Transactional
    public Transaction updateTransactionPhoto(Long id, String photoPath) {

        Transaction transaction = getTransactionById(id);
        transaction.setPhotoPath(photoPath);

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(Long id) {

        Transaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }

    @Override
    public BigDecimal getCustomerBalance(Long customerId) {

        Owner currentOwner = ownerService.getCurrentOwner();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("ग्राहक सापडला नाही! ID: " + customerId));

        // Verify customer belongs to current owner
        if (!customer.getOwner().getId().equals(currentOwner.getId())) {
            throw new RuntimeException("तुम्हाला या ग्राहकाची माहिती पाहण्याची परवानगी नाही!");
        }

        return customer.getBalance();
    }
}