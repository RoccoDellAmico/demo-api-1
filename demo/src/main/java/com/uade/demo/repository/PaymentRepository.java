package com.uade.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.demo.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(value = "SELECT p FROM Payment p WHERE p.id_user = ?1", nativeQuery = true)
    List<Payment> findByUserId(Long UserId);
}