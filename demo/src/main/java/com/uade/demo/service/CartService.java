package com.uade.demo.service;

import java.util.List;

import com.uade.demo.entity.Size;
import com.uade.demo.entity.dto.CartDTO;
import com.uade.demo.entity.dto.CartProductDTO;
import com.uade.demo.exceptions.ItemNotFoundException;

public interface CartService {
    CartDTO addProduct(Long cartId, Long productId, Size size,int quantity) throws ItemNotFoundException;
    CartDTO removeProduct(Long cartId, Long productId) throws ItemNotFoundException;
    CartDTO updateProductQuantity(Long cartId, Size size,Long productId, int newQuantity) throws ItemNotFoundException;
    CartDTO addOneProduct(Long cartId, Size size,Long productId) throws ItemNotFoundException;
    CartDTO substractOneProduct(Long cartId, Long productId) throws ItemNotFoundException;
    CartDTO clearCart(Long cartId) throws ItemNotFoundException;
    List<CartProductDTO> getCartProducts(Long cartId) throws ItemNotFoundException;
    double getTotal(Long cartId) throws ItemNotFoundException;
    int getItemCount(Long cartId) throws ItemNotFoundException;
    List<CartDTO> getCarts();
    CartDTO getCartsByUser(Long userId);
    CartDTO createCart(String email) throws ItemNotFoundException; 
    CartDTO getCartById(Long cartId) throws ItemNotFoundException;
}
