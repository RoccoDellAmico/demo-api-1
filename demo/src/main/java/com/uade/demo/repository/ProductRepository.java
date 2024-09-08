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

    @Query(value = "select p from Product p where p.stock > 0")
    List<Product> findByCategories(Long categoryId);

    @Query(value = "select p from Product p where p.price > ?1 and p.price < ?2")
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    @Query(value = "select p from Product p where p.league = ?1")
    List<Product> findByLeague(String league);

    @Query(value = "select p from Product p where p.club = ?1")
    List<Product> findByClub(String club);
}
