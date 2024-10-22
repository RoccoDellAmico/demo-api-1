package com.uade.demo.service;

import java.util.List;
import java.util.Map;

import com.uade.demo.entity.ClientCategory;
import com.uade.demo.entity.Product;
import com.uade.demo.entity.Size;
import com.uade.demo.entity.TypeOfProduct;
import com.uade.demo.entity.dto.ProductDTO;

public interface ProductService {
    public List<Product> getProducts();

    public ProductDTO getProductById(Long productId);

    public ProductDTO getProductByDescr(String description);

    public List<ProductDTO> getProductByCategoryId(Long categoryId);

    public List<ProductDTO> getProductsByPriceRange(double minPrice, double maxPrice);

    public List<ProductDTO> getProductsByLeague(String league);

    public List<ProductDTO> getProductsByClub(String club);

    public List<ProductDTO> getProductBySize(Size size);

    public ProductDTO updateProductPrice(Long id, double price);

    public ProductDTO updateProductStock(Long id, Size size, int stock);

    //public void saveProduct(Product newProduct);

    public Product createProduct(String description, double price, Map<Size, Integer> productStock,
    String club, String league, List<String> photos, ClientCategory clientCategory, TypeOfProduct typeOfProduct, 
    int year);

    public void deleteProduct(Long productId);

    public List<ProductDTO> getProductsUser();

    public ProductDTO addProductSize(Long id, Size size, int stock);

    public Product updateProduct(Long id, String description, double price, Map<Size, Integer> productStock,
        String club, String league, List<String> photos, ClientCategory clientCategory, TypeOfProduct typeOfProduct, 
        int year);

}
