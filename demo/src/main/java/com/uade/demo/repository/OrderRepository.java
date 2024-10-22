package com.uade.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.demo.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    @Query(value = "SELECT o.* FROM Orders o WHERE o.user_id = ?1", nativeQuery = true)
    List<Order> findByUserId(Long UserId);
}
