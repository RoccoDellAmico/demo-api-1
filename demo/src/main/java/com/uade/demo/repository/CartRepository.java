package com.uade.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.demo.entity.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    Optional<Cart> findByCartId(Long cartId);
    @Query(value = "select c from Cart c where c.user = ?1")
    List<Cart> findCartByUser(Long userId);
}
