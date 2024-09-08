package com.uade.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.demo.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{}
