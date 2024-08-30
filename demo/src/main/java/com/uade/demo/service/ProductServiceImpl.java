package com.uade.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.demo.entity.Product;
import com.uade.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(PageRequest pageRequest){
        return productRepository.findAll(pageRequest);
    }

    public Optional<Product> getProductById(Long productId){
        return productRepository.findById(productId);
    }

    public Product createProduct(String description, double price, String club, String league){
        return productRepository.save(new Product(description, price, club, league));
    }
}
