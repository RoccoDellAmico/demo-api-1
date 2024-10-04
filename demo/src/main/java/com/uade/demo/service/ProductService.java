package com.uade.demo.service;

import java.util.List;
import java.util.Map;

import com.uade.demo.entity.Size;
import com.uade.demo.entity.dto.ProductDTO;
import com.uade.demo.exceptions.CategoryDuplicateException;

public interface ProductService {
    public List<ProductDTO> getProducts();

    public ProductDTO getProductById(Long productId);

    public ProductDTO getProductByDescr(String description);

    public List<ProductDTO> getProductByCategoryId(Long categoryId);

    public List<ProductDTO> getProductsByPriceRange(double minPrice, double maxPrice);

    public List<ProductDTO> getProductsByLeague(String league);

    public List<ProductDTO> getProductsByClub(String club);

    public List<ProductDTO> getProductByCategoryDescr(String description); 

    public List<ProductDTO> getProductBySize(Size size);

    public ProductDTO addProductCategory(Long id, String description) throws CategoryDuplicateException;

    public ProductDTO deleteProductCategory(Long id, String description);

    public ProductDTO updateProductPrice(Long id, double price);

    public ProductDTO updateProductStock(Long id, Size size, int stock);

    //public void saveProduct(Product newProduct);

    public ProductDTO createProduct(String description, double price, Map<Size, Integer> productStock,
    String club, String league, List<String> photos);

    public String deleteProduct(Long productId);

    public List<ProductDTO> getProductsUser();

    public ProductDTO addProductSize(Long id, Size size, int stock);

}
