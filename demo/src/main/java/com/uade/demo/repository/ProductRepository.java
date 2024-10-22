package com.uade.demo.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.demo.entity.ClientCategory;
import com.uade.demo.entity.Product;
import com.uade.demo.entity.Size;
import com.uade.demo.entity.TypeOfProduct;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    @Query(value = "select p.* from Product p inner join Product_Stock ps on p.id = ps.product_id where p.id = ?1 group by p.id having sum(ps.stock)>0", nativeQuery = true)
    Optional<Product> findById(Long id);

    @Query(value = "select p.* from Product p where p.id = ?1", nativeQuery = true)
    Optional<Product> findByIdAdmin(Long id);

    @Query(value = "select p.* from Product p inner join product_stock ps on p.id = ps.product_id where p.description = ?1 group by p.id having sum(ps.stock)>0", nativeQuery = true)
    Product findByDescription(String description); //s

    @Query(value = "SELECT p.* FROM Product p JOIN Product_Category pc ON p.id = pc.product_Id JOIN Category c ON pc.category_Id = c.id inner join product_stock ps on p.id = ps.product_id WHERE c.id = ?1 group by p.id having sum(ps.stock)>0", nativeQuery = true)
    List<Product> findByCategories(Long categoryId); 

    @Query(value = "select p.* from Product p inner join Product_Stock ps on p.id = ps.product_Id where ps.size = ?1 and ps.stock > 0", nativeQuery = true)
    List<Product> findBySize(Size size); 

    @Query(value = "select p.* from Product p inner join product_stock ps on p.id = ps.product_id where p.price > ?1 and p.price < ?2 group by p.id having sum(ps.stock)>0", nativeQuery = true)
    List<Product> findByPriceBetween(double minPrice, double maxPrice); 

    @Query(value = "select p.* from Product p inner join product_stock ps on p.id = ps.product_id where p.league = ?1 group by p.id having sum(ps.stock) > 0", nativeQuery = true)
    List<Product> findByLeague(String league); 

    @Query(value = "select p.* from Product p inner join product_stock ps on p.id = ps.product_id where p.club = ?1 group by p.id having sum(ps.stock)>0", nativeQuery = true)
    List<Product> findByClub(String club); 

    @Query(value = "select p.* from Product p inner join product_stock ps on p.id = ps.product_id group by p.id having sum(ps.stock)>0", nativeQuery = true)
    List<Product> findAvailableProducts(); 

    @Query(value = "select p.* from Product p inner join product_stock ps on p.id = ps.product_id where p.client_category = ?1 group by p.id having sum(ps.stock)>0", nativeQuery = true)
    List<Product> findByClientCategory(ClientCategory clientCategory);

    @Query(value = "select p.* from Product p inner join product_stock ps on p.id = ps.product_id where p.year = ?1 group by p.id having sum(ps.stock)>0", nativeQuery = true)
    List<Product> findByYear(int year);

    @Query(value = "select p.* from Product p inner join product_stock ps on p.id = ps.product_id where p.type_of_product = ?1 group by p.id having sum(ps.stock)>0", nativeQuery = true)
    List<Product> findByTypeOfProduct(TypeOfProduct typeOfProduct);
}
