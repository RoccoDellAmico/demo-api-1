package com.uade.demo.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.demo.entity.Category;
import com.uade.demo.entity.Product;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.repository.CategoryRepository;
import com.uade.demo.entity.Size;
import com.uade.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoyRepository;

    public Page<Product> getProducts(PageRequest pageRequest){
        return productRepository.findAll(pageRequest);
    }

    public Optional<Product> getProductById(Long productId){
        return productRepository.findById(productId);
    }

    public List<Product> getProductByCategory(Long categoryId) {
        return productRepository.findByCategories(categoryId);
    }

    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
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

    public Optional<Product> addProductCategory(Long id, String description) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            Category category = categoyRepository.findByDescription(description).orElseGet(() -> {
                Category newCategory = new Category(description);
                return categoyRepository.save(newCategory);
            });
            product.addProductCategory(category);
            productRepository.save(product);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> deleteProductCategory(Long id, String description) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Optional<Category> categoryOptional = categoyRepository.findByDescription(description);
            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                product.removeProductCategory(category);
                productRepository.save(product);
                return Optional.of(product);
            }
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> updateProductPrice(Long id, double price) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            product.setPrice(price);
            productRepository.save(product);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> updateProductStock(Long id, int stock) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            product.setStock(stock);
            productRepository.save(product);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Transactional(rollbackFor = Throwable.class)
    public Product createProduct(String description, double price, Size size, int stock,
    String club, String league, List<String> photos) {
        return productRepository.save(new Product(description, price, size, stock, club, league,photos));
    }

    @Transactional(rollbackFor = Throwable.class)
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);

        productRepository.delete(product);

        return "Product with productId: " + productId + " deleted successfully !!!";
    }

}
