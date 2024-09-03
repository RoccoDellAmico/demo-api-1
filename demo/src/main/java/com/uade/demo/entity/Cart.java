package com.uade.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProduct> cartProducts = new ArrayList<>();

    @ManyToOne
    private User user;

    public Cart(User user){
        this.user = user;
    }

    public Cart(){}

    public Cart(Long cartId, List<CartProduct> cartProducts, User user){
        this.cartId = cartId;
        this.cartProducts = cartProducts;
        this.user = user;
    }

    public Cart(Long cartId, User user){
        this.cartId = cartId;
        this.user = user;
    }

    public void addProduct(CartProduct cartProduct){
        cartProducts.add(cartProduct);
        cartProduct.setCart(this);
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
