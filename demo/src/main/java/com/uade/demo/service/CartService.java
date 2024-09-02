package com.uade.demo.service;

import java.util.List;

import com.uade.demo.entity.CartProduct;
import com.uade.demo.exceptions.ItemNotFoundException;

public interface CartService {
    void addProduct(Long cartId, Long productId, int quantity) throws ItemNotFoundException;
    void removeProduct(Long cartId, Long productId) throws ItemNotFoundException;
    void updateProductQuantity(Long cartId, Long productId, int newQuantity) throws ItemNotFoundException;
    void addOneProduct(Long cartId, Long productId) throws ItemNotFoundException;
    void substractOneProduct(Long cartId, Long productId) throws ItemNotFoundException;
    void clearCart(Long cartId) throws ItemNotFoundException;
    List<CartProduct> getCartProducts(Long cartId) throws ItemNotFoundException;
    double getTotal(Long cartId) throws ItemNotFoundException;
    int getItemCount(Long cartId) throws ItemNotFoundException;
}
