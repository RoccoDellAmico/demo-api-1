package com.uade.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProduct> cartProducts = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "active", columnDefinition = "TINYINT(1)")
    private boolean active = true;

    public Cart(User user){
        this.user = user;
    }

    public Cart(){}

    public Cart(Long cartId, List<CartProduct> cartProducts, User user, boolean active){
        this.cartId = cartId;
        this.cartProducts = cartProducts;
        this.user = user;
        this.active = active;
    }

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
        double total = 0;
        for(CartProduct cartProduct : cartProducts){
            total += cartProduct.getProduct().getPrice() * cartProduct.getQuantity();
        }
        return total;
    }

    public int getItemCount(){
        int itemCount = 0;
        for(CartProduct cartProduct : cartProducts){
            itemCount += cartProduct.getQuantity();
        }
        return itemCount;
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

    public void notActive(){
        this.active = false;
    }

    public boolean isActive(){
        return this.active;
    }
}
