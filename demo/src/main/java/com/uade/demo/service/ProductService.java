package com.uade.demo.service;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.demo.entity.Product;

public interface ProductService {
    public Page<Product> getProducts(PageRequest pageRequest);

    public Optional<Product> getProductById(Long productId);

    public List<Product> getProductByCategory(Long categoryId);

    public Product createProduct(String description, double price, String club, String league);

    public String deleteProduct(Long productId);
}
