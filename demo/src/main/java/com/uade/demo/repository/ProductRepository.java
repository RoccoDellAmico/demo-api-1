package com.uade.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.demo.entity.Category;
import com.uade.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    Optional<Product> findById(Long id);

    Optional<Product> findByCategories(Optional<Category> categeory);
}
