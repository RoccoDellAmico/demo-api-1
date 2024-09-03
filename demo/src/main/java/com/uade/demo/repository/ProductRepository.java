package com.uade.demo.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    @Query(value = "select p from Product p where p.id = ?1 and p.stock > 0")
    Optional<Product> findById(Long id);

    @Query(value = "select p from Product p where p.category_id = ?1 and p.stock > 0")
    List<Product> findByCategories(Long categeoryId);

    Optional<Product> findByPriceBetween(double maxPrice);

    Optional<Product> findByLeague(String league);

    Optional<Product> findByClub(String club);
}
