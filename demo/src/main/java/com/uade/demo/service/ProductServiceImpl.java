package com.uade.demo.service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.demo.entity.ClientCategory;
import com.uade.demo.entity.Product;
import com.uade.demo.entity.Size;
import com.uade.demo.entity.TypeOfProduct;
import com.uade.demo.entity.dto.ProductDTO;
import com.uade.demo.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(){
        List<Product> products = productRepository.findAll();
        return products;
    }

    public List<ProductDTO> getProductsUser(){
        List<Product> products = productRepository.findAvailableProducts();
        return mapToListProductDTOs(products);
    }

    public ProductDTO getProductById(Long productId){
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            return mapToProductDTO(product);
        }
        return null;
    }

    public ProductDTO getProductByDescr(String description){
        Product product = productRepository.findByDescription(description);
        return mapToProductDTO(product);
    }

    public List<ProductDTO> getProductByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategories(categoryId);
        return mapToListProductDTOs(products);
    }

    public List<ProductDTO> getProductsByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return mapToListProductDTOs(products);
    }

    public List<ProductDTO> getProductsByLeague(String league) {
        List<Product> products = productRepository.findByLeague(league);
        return mapToListProductDTOs(products);
    }

    public List<ProductDTO> getProductsByClub(String club) {
        List<Product> products = productRepository.findByClub(club);
        return mapToListProductDTOs(products);
    }

    @Override
    public List<ProductDTO> getProductBySize(Size size) {
        List<Product> products = productRepository.findBySize(size);
        List<ProductDTO> productDTOs = mapToListProductDTOs(products);
        return productDTOs;
    }

    @Override
    public ProductDTO updateProductPrice(Long id, double price) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            product.setPrice(price);
            productRepository.save(product);
            return mapToProductDTO(product);
        }
        return null;
    }

    @Override
    public ProductDTO updateProductStock(Long id, Size size, int stock) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            product.updateProductStock(size, stock);
            productRepository.save(product);
            return mapToProductDTO(product);
        }
        return null;
    }

    @Transactional(rollbackFor = Throwable.class)
    public ProductDTO createProduct(String description, double price, Map<Size, Integer> productStock,
    String club, String league, List<String> photos, ClientCategory clientCategory, TypeOfProduct typeOfProduct, 
    int year) {
        Product product = new Product(description, price, productStock, club, league, photos, clientCategory, 
        typeOfProduct, year);
        Product savedProduct = productRepository.save(product);
        return mapToProductDTO(savedProduct);
    }

    /*@Transactional(rollbackFor = Throwable.class)
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);

        productRepository.delete(product);

        return "Product with productId: " + productId + " deleted successfully !!!";
    }*/

    public void deleteProduct(Long productId){
        Optional<Product> productOptional = productRepository.findByIdAdmin(productId);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            productRepository.delete(product);
        }
    }

    public ProductDTO addProductSize(Long id, Size size, int stock){
        Product product = productRepository.findById(id).orElse(null);
        if(product != null){
            product.addProductSize(size, stock);
            productRepository.save(product);
            return mapToProductDTO(product);
        }
        return null;
    }

    private ProductDTO mapToProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setPhotos(product.getPhotos());
        productDTO.setCategory(product.getClientCategory().toString());
        productDTO.setLeague(product.getLeague());
        productDTO.setTypeOfProduct(product.getTypeOfProduct());
        productDTO.setProductStock(product.getProductStock());
        return productDTO;
    }

    private List<ProductDTO> mapToListProductDTOs(List<Product> products){
        List<ProductDTO> productDTOs = new ArrayList<>();
        for(Product product : products){
            productDTOs.add(mapToProductDTO(product));
        }
        return productDTOs;
    }

    @Override
    public ProductDTO updateProduct(Long id, String description, double price, Map<Size, Integer> productStock,
            String club, String league, List<String> photos, ClientCategory clientCategory, TypeOfProduct typeOfProduct,
            int year) {
        Optional<Product> productOptional = productRepository.findByIdAdmin(id);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.setDescription(description);
            product.setPrice(price);
            product.setProductStock(productStock);
            product.setClub(club);
            product.setLeague(league);
            product.setPhotos(photos);
            product.setClientCategory(clientCategory);
            product.setTypeOfProduct(typeOfProduct);
            product.setYear(year);
            return mapToProductDTO(productRepository.save(product));
        }
        return null;
    }

}
