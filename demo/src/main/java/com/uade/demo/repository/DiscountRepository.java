package com.uade.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.demo.entity.Discount;
import java.util.Optional;


@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query
    (value = "select d from Discount d where d.code = ?1")
    Optional<Discount> findByCode(String code);
}
