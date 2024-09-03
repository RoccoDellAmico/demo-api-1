package com.uade.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.demo.entity.Category;
import com.uade.demo.entity.Product;
import com.uade.demo.repository.CategoryRepository;
import com.uade.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository CategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(PageRequest pageRequest){
        return productRepository.findAll(pageRequest);
    }

    public Optional<Product> getProductById(Long productId){
        return productRepository.findById(productId);
    }

    public Optional<Product> getProductByCategory(Long categoryId) {
        Optional<Category> categeory = CategoryRepository.findById(categoryId);

        if (!categeory.isPresent())
            return Optional.empty();

        return productRepository.findByCategories(categeory);

    }

    public Optional<Product> getProductsByPriceRange(double maxPrice) {
        Optional<Product> products = productRepository.findByPriceBetween(maxPrice);

        if (products.isEmpty()) {
            return Optional.empty();
        }

        return products;
    }


    public Optional<Product> getProductsByLeague(String league) {
        Optional<Product> products = productRepository.findByLeague(league);

        if (products.isEmpty()) {
            return Optional.empty();
        }

        return products;
    }

    public Optional<Product> getProductsByClub(String club) {
        Optional<Product> products = productRepository.findByClub(club);

        if (products.isEmpty()) {
            return Optional.empty();
        }

        return products;
    }

    public Product createProduct(String description, double price, String club, String league){
        return productRepository.save(new Product(description, price, club, league));
    }

    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);

        productRepository.delete(product);

        return "Product with productId: " + productId + " deleted successfully !!!";
    }

}
