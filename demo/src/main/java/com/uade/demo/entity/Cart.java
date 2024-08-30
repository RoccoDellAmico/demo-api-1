package com.uade.demo.entity;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> cart;
    
    public Cart(){
        this.cart = new HashMap<>();
    }

    public void addProduct(Product product, Integer quantity){
        if(cart.containsKey(product)){
            cart.put(product, cart.get(product) + quantity);
        }
        else{
            cart.put(product, quantity);
        }
    }

    public void removeProduct(Product product){
        cart.remove(product);
    }

    public void clearCart(){
        cart.clear();
    }

    public Map<Product, Integer> getCartItems(){
        return cart;
    }

    public double getTotal(){
        return cart.entrySet()
                        .stream()
                        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                        .sum();
    }

    public int getItemCount(){
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }

}
