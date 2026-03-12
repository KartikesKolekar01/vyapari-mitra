package com.vyaparimitra.vyapari_mitra.repository;

import com.vyaparimitra.vyapari_mitra.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find by owner ID
    List<Customer> findByOwnerId(Long ownerId);

    // Find by owner ID and name
    List<Customer> findByOwnerIdAndNameContainingIgnoreCase(Long ownerId, String name);

    // Find by owner ID and mobile
    Optional<Customer> findByOwnerIdAndMobile(Long ownerId, String mobile);

    // Find by owner ID and balance > 0
    List<Customer> findByOwnerIdAndBalanceGreaterThan(Long ownerId, BigDecimal amount);

    // ✅ Count customers by owner ID - ही method add करा
    long countByOwnerId(Long ownerId);

    // Search by name or mobile (with owner filter)
    @Query("SELECT c FROM Customer c WHERE c.owner.id = :ownerId AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR c.mobile LIKE CONCAT('%', :search, '%'))")
    List<Customer> searchCustomers(@Param("ownerId") Long ownerId, @Param("search") String search);

    // Find by village (with owner filter)
    List<Customer> findByOwnerIdAndVillageIgnoreCase(Long ownerId, String village);
}