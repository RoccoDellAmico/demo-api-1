package com.uade.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.demo.entity.Cart;
import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long>{
    Optional<Cart> findByCartId(Long cartId);
}
