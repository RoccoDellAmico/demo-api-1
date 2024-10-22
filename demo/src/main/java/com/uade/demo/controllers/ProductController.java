package com.uade.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.demo.service.ProductService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.uade.demo.entity.Product;
import com.uade.demo.entity.Size;
import com.uade.demo.entity.dto.AddSizeRequest;
import com.uade.demo.entity.dto.ProductDTO;
import com.uade.demo.entity.dto.ProductRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/admin/products/get")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }
    
    @GetMapping("/public/products/get")
    public ResponseEntity<List<ProductDTO>> getProductsUser() {
        return ResponseEntity.ok(productService.getProductsUser());
    }

    @GetMapping("/public/products/id/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/public/products/description/{description}")
    public ResponseEntity<ProductDTO> getProductByDescr(@PathVariable String description) {
        return ResponseEntity.ok(productService.getProductByDescr(description));
    }

    @GetMapping("/public/categories/{categoryId}/products/id")
    public ResponseEntity<List<ProductDTO>> getProduductByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductByCategoryId(categoryId));
    }

    @GetMapping("/public/products/price/{minPrice}/{maxPrice}")
    public ResponseEntity<List<ProductDTO>> getProductsByPriceRange(@PathVariable double minPrice, 
        @PathVariable double maxPrice) {
        return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/public/products/league/{league}")
    public ResponseEntity<List<ProductDTO>> getProductsByLeague(@PathVariable String league) {
        return ResponseEntity.ok(productService.getProductsByLeague(league));
    }
    
    @GetMapping("/public/products/club/{club}")
    public ResponseEntity<List<ProductDTO>> getProductsByClub(@PathVariable String club) {
        return ResponseEntity.ok(productService.getProductsByClub(club));
    }

    @GetMapping("/public/products/size/{size}")
    public ResponseEntity<List<ProductDTO>> getProductsBySize(@PathVariable Size size) {
        return ResponseEntity.ok(productService.getProductBySize(size));
    }

    @PutMapping("/admin/products/{productId}/price/{newPrice}/update")
    public ResponseEntity<ProductDTO> updateProductPrice(@PathVariable Long productId, @PathVariable double newPrice) {
        return ResponseEntity.ok(productService.updateProductPrice(productId, newPrice));
    }

    @PutMapping("/admin/products/{productId}/{size}/stock/{newStock}/update")
    public ResponseEntity<ProductDTO> updateProductStock(@PathVariable Long productId, @PathVariable Size size, 
        @PathVariable int newStock) {
        return ResponseEntity.ok(productService.updateProductStock(productId, size, newStock));
    }

    @PostMapping("/admin/products")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest.getDescription(), productRequest.getPrice(), 
            productRequest.getProductStock(), productRequest.getClub(), productRequest.getLeague(), 
            productRequest.getPhotos(), productRequest.getClientCategory(), productRequest.getTypeOfProduct(), 
            productRequest.getYear());
        return ResponseEntity.ok(product);
    }

    @PutMapping("/admin/products/update")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest productRequest){
        Product product = productService.updateProduct(productRequest.getId(), productRequest.getDescription(), 
            productRequest.getPrice(), productRequest.getProductStock(), productRequest.getClub(), 
            productRequest.getLeague(), productRequest.getPhotos(), productRequest.getClientCategory(), 
            productRequest.getTypeOfProduct(), productRequest.getYear());
        return ResponseEntity.ok(product);
    }
    
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<Object> deleteProductById(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/products/addSize")
    public ResponseEntity<ProductDTO> addProductSize(@RequestBody AddSizeRequest addSizeRequest) {
        ProductDTO product = productService.addProductSize(addSizeRequest.getProductId(), addSizeRequest.getSize(), 
            addSizeRequest.getStock());
        return ResponseEntity.ok(product);
    }
}
