package com.uade.demo.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.uade.demo.entity.Product;
import com.uade.demo.entity.Size;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    @Query(value = "select p from Product p where p.id = ?1")
    Optional<Product> findById(Long id);

    @Query(value = "select p from Product p where p.description = ?1 and p.stock > 0")
    Optional<Product> findByDescription(String description);

    @Query(value = "SELECT p.* FROM Product p JOIN Product_Category pc ON p.id = pc.product_Id JOIN Category c ON pc.category_Id = c.id WHERE c.id = ?1 AND p.stock > 0", nativeQuery = true)
    List<Product> findByCategories(Long categoryId);

    @Query(value = "select p from Product p where p.size = ?1 and  p.stock > 0")
    List<Product> findBySize(Size size);

    @Query(value = "select p from Product p where p.price > ?1 and p.price < ?2 and p.stock > 0")
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    @Query(value = "select p from Product p where p.league = ?1 and p.stock > 0")
    List<Product> findByLeague(String league);

    @Query(value = "select p from Product p where p.club = ?1 and p.stock > 0")
    List<Product> findByClub(String club);
}
