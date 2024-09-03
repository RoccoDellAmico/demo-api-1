package com.uade.demo.service;

import java.util.List;

import com.uade.demo.entity.Cart;
import com.uade.demo.entity.CartProduct;
import com.uade.demo.exceptions.ItemNotFoundException;

public interface CartService {
    Cart addProduct(Long cartId, Long productId, int quantity) throws ItemNotFoundException;
    Cart removeProduct(Long cartId, Long productId) throws ItemNotFoundException;
    Cart updateProductQuantity(Long cartId, Long productId, int newQuantity) throws ItemNotFoundException;
    Cart addOneProduct(Long cartId, Long productId) throws ItemNotFoundException;
    Cart substractOneProduct(Long cartId, Long productId) throws ItemNotFoundException;
    Cart clearCart(Long cartId) throws ItemNotFoundException;
    List<CartProduct> getCartProducts(Long cartId) throws ItemNotFoundException;
    double getTotal(Long cartId) throws ItemNotFoundException;
    int getItemCount(Long cartId) throws ItemNotFoundException;
    List<Cart> getCarts();
    List<Cart> getCartsByUser(Long userId);
    Cart createCart(String email) throws ItemNotFoundException; 
}
