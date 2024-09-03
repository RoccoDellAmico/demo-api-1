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

    /* Metodo AddProduct verificando el stock
    public void addProduct(Product product, Integer quantity, String size) {
        Integer availableStock = product.getStock().get(size);
    
        if (availableStock == null) {
            System.out.println("El tamaño especificado no está disponible.");
        } else if (availableStock < quantity) {
            System.out.println("No hay suficiente stock disponible para la cantidad solicitada.");
        } else {
            if (cart.containsKey(product)) {
                cart.put(product, cart.get(product) + quantity);
            } else {
                cart.put(product, quantity);
            }
            product.getStock().put(size, availableStock - quantity); // Reduce stock
        }
    }
    */
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
