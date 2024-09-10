package com.uade.demo.service;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.demo.entity.Product;
import com.uade.demo.entity.Size;
import com.uade.demo.exceptions.CategoryDuplicateException;

public interface ProductService {
    public Page<Product> getProducts(PageRequest pageRequest);

    public Optional<Product> getProductById(Long productId);

    public Optional<Product> getProductByDescr(String description);

    public List<Product> getProductByCategoryId(Long categoryId);

    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice);

    public List<Product> getProductsByLeague(String league);

    public List<Product> getProductsByClub(String club);

    public Optional<List<Product>> getProductByCategoryDescr(String description); 

    public List<Product> getProductBySize(Size size);

    public Optional<Product> addProductCategory(Long id, String description) throws CategoryDuplicateException;

    public Optional<Product> deleteProductCategory(Long id, String description);

    public Optional<Product> updateProductPrice(Long id, double price);

    public Optional<Product> updateProductStock(Long id, int stock);

    //public void saveProduct(Product newProduct);

    public Product createProduct(String description, double price, Size size, int stock,
    String club, String league, List<String> photos);

    public String deleteProduct(Long productId);

}
