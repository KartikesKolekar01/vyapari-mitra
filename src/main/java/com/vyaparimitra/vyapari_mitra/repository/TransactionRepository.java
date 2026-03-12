package com.vyaparimitra.vyapari_mitra.repository;

import com.vyaparimitra.vyapari_mitra.model.Transaction;
import com.vyaparimitra.vyapari_mitra.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find by customer (with owner verification in service)
    List<Transaction> findByCustomerOrderByTransactionDateDesc(Customer customer);

    // Find by owner and type
    List<Transaction> findByOwnerIdAndTypeOrderByTransactionDateDesc(Long ownerId, String type);

    // ✅ Find by owner and date (ही method हवी)
    List<Transaction> findByOwnerIdAndTransactionDate(Long ownerId, LocalDate date);

    // Find by owner and date between
    List<Transaction> findByOwnerIdAndTransactionDateBetween(Long ownerId, LocalDate startDate, LocalDate endDate);

    // Find pending payments for owner
    @Query("SELECT t FROM Transaction t WHERE t.owner.id = :ownerId AND t.type = 'CREDIT' AND t.dueDate < :currentDate AND t.customer.balance > 0")
    List<Transaction> findPendingPayments(@Param("ownerId") Long ownerId, @Param("currentDate") LocalDate currentDate);

    // Find by customer and type (with owner verification)
    List<Transaction> findByCustomerAndTypeOrderByTransactionDateDesc(Customer customer, String type);
}