package com.uade.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private List<CartProduct> cartProducts = new ArrayList<>();

    public void addProduct(CartProduct cartProduct){
        cartProducts.add(cartProduct);
    }

    public void removeProduct(Product product){
        int index = -1;
        for(CartProduct cartProduct : cartProducts){
            if(cartProduct.getProduct().getId() == product.getId())
                index = cartProducts.indexOf(cartProduct);
        }
        cartProducts.remove(index);
    }

    public void clearCart(){
        cartProducts.clear();
    }

    public List<CartProduct> getCartProducts(){
        return cartProducts;
    }

    public double getTotal(){
        return 1000;
    }

    public int getItemCount(){
        return cartProducts.size();
    }

    public boolean hasProduct(Long productId){
        for(CartProduct cartProduct : cartProducts){
            if(cartProduct.getProduct().getId() == productId)
                return true;
        }
        return false;
    }

    public CartProduct getCartProductByProductId(Long productId){
        for(CartProduct cartProduct : cartProducts){
            if(cartProduct.getProduct().getId() == productId)
                return cartProduct;
        }
        return null;
    }

    public void updateProductQuantity(Long productId, int newQuantity){
        for(CartProduct cartProduct : cartProducts){
            if(cartProduct.getProduct().getId() == productId)
                cartProduct.setQuantity(newQuantity);
        }
    }

    public int getCartProductQuantity(Long productId){
        for(CartProduct cartProduct : cartProducts){
            if(cartProduct.getProduct().getId() == productId)
                return cartProduct.getQuantity();
        }
        return 0;
    }

}
