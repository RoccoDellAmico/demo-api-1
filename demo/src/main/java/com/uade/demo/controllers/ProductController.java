package com.uade.demo.controllers;

import java.util.Optional;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uade.demo.service.CategoryService;
import com.uade.demo.service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.demo.entity.Product;
import com.uade.demo.entity.Category;
import com.uade.demo.entity.dto.ProductRequest;
import com.uade.demo.exceptions.ItemNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/public/products")
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
                return ResponseEntity.ok(productService.getProducts(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(productService.getProducts(PageRequest.of(page, size)));
    }
    
    @GetMapping("/public/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Optional<Product> result = productService.getProductById(productId);
        if(result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<List<Product>> getProduductByCategory(@PathVariable Long categoryId) {
        List<Product> result = productService.getProductByCategory(categoryId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("public/products/price/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@PathVariable double minPrice,  double maxPrice) {
        List<Product> result = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(result);
    }

    @GetMapping("public/products/league/{league}")
    public ResponseEntity<Product> getProductsByLeague(@PathVariable String league) {
        Optional<Product> result = productService.getProductsByLeague(league);
        if(result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("public/products/club/{club}")
    public ResponseEntity<Product> getProductsByClub(@PathVariable String club) {
        Optional<Product> result = productService.getProductsByClub(club);
        if(result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("admin/products/{productId}/category/{categoryDescription}/update/add")
    public ResponseEntity<Product> addProductCategory(@PathVariable Long id, @RequestBody String description) {
        Optional<Product> product = productService.addProductCategory(id, description);
        if(product.isPresent())
            return ResponseEntity.ok(product.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("admin/products/{productId}/category/{categoryDescription}/update/delete")
    public ResponseEntity<Product> deleteProductCategory(@PathVariable Long id, @RequestBody String description)   {
        Optional<Product> product = productService.deleteProductCategory(id, description);
        if(product.isPresent())
            return ResponseEntity.ok(product.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/products/{productId}/price/{newPrice}/update")
    public ResponseEntity<Product> updateProductPrice(@PathVariable Long id, @RequestParam double newPrice) {
        Optional<Product> product = productService.updateProductPrice(id, newPrice);
        if(product.isPresent())
            return ResponseEntity.ok(product.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/products/{productId}/stock/{newStock}/update")
    public ResponseEntity<Product> updateProductStock(@PathVariable Long id, @RequestParam int newStock) {
        Optional<Product> product = productService.updateProductStock(id, newStock);
        if(product.isPresent())
            return ResponseEntity.ok(product.get());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/products")
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequest productRequest) {
        Product result = productService.createProduct(productRequest.getDescription(), productRequest.getPrice(), 
            productRequest.getSize(), productRequest.getStock(), 
            productRequest.getClub(), productRequest.getLeague(), productRequest.getPhotos());
        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
    }
    
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<Object> deleteProductById(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
