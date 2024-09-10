package com.uade.demo.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.demo.controllers.YourCustomException;
import com.uade.demo.entity.Category;
import com.uade.demo.entity.Product;
import com.uade.demo.exceptions.CategoryDuplicateException;
import com.uade.demo.repository.CategoryRepository;
import com.uade.demo.entity.Size;
import com.uade.demo.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Product> getProducts(PageRequest pageRequest){
        return productRepository.findAll(pageRequest);
    }

    public Optional<Product> getProductById(Long productId){
        return productRepository.findById(productId);
    }

    public Optional<Product> getProductByDescr(String description){
        return productRepository.findByDescription(description);
    }

    public List<Product> getProductByCategoryId(Long categoryId) {
        return productRepository.findByCategories(categoryId);
    }

    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return products;
    }

    public List<Product> getProductsByLeague(String league) {
        List<Product> products = productRepository.findByLeague(league);
        return products;
    }

    public List<Product> getProductsByClub(String club) {
        List<Product> products = productRepository.findByClub(club);
        return products;
    }

    @Override
    public Optional<List<Product>> getProductByCategoryDescr(String description) {
        Optional<Category> categoryOptional = categoryRepository.findByDescription(description);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            Long categoryId = category.getId();
            List<Product> products = productRepository.findByCategories(categoryId);
            return Optional.of(products);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getProductBySize(Size size) {
        return productRepository.findBySize(size);
    }

    public Optional<Product> addProductCategory(Long id, String description) 
        throws CategoryDuplicateException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product product = productOptional.get();

            Optional<Category> categoryOptional = categoryRepository.findByDescription(description);
            
            if (categoryOptional.isEmpty()) {
                categoryOptional = Optional.of(new Category(description));
                categoryRepository.save(categoryOptional.get());
            }

            Category category = categoryOptional.get();

            List<Category> categories = product.getCategories();
            if (categories.contains(category)) {
                throw new YourCustomException("Categoria duplicada");}
            
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
            Optional<Category> categoryOptional = categoryRepository.findByDescription(description);
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
