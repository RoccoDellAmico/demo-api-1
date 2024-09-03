package com.uade.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.demo.entity.Cart;
import com.uade.demo.entity.CartProduct;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.service.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")
public class CartController {
    //implementar crear carrito, metodos controller
    //implementar cartDTO
    @Autowired
    private CartService cartService;

    @GetMapping("/admin/carts")
    public ResponseEntity<List<Cart>> getCarts(){
        List<Cart> carts = cartService.getCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/admin/carts/{userId}")
    public ResponseEntity<List<Cart>> getCartsByUser(@PathVariable Long userId){
        List<Cart> carts = cartService.getCartsByUser(userId);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/public/carts/{email}")
    public ResponseEntity<Object> createCart(@PathVariable String email) throws ItemNotFoundException {
        Cart cart = cartService.createCart(email);
        return ResponseEntity.created(URI.create("cart" + cart.getCartId())).body(cart);
    }
    
    @PutMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
    public ResponseEntity<Object> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId,
        @PathVariable int quantity) throws ItemNotFoundException {
        Cart cart = cartService.addProduct(cartId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/public/carts/{cartId}/products/{productId}/addOne")
    public ResponseEntity<Object> addOneProduct(@PathVariable Long cartId, @PathVariable Long productId) throws ItemNotFoundException {
        Cart cart = cartService.addOneProduct(cartId, productId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/public/carts/{cartId}/products/{productId}/substractOne")
    public ResponseEntity<Object> substractOneProduct(@PathVariable Long cartId, @PathVariable Long productId) 
        throws ItemNotFoundException {
        Cart cart = cartService.substractOneProduct(cartId, productId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/public/carts/{cartId}/products/{productId}/quantity/{newQuantity}/update")
    public ResponseEntity<Object> updateProductQuantity(@PathVariable Long cartId, 
        @PathVariable Long productId, @PathVariable int newQuantity) throws ItemNotFoundException {
        Cart cart = cartService.updateProductQuantity(cartId, productId, newQuantity);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/public/carts/{cartId}/products/{productId}/remove")
    public ResponseEntity<Object> removeProduct(@PathVariable Long cartId, @PathVariable Long productId) 
        throws ItemNotFoundException {
        Cart cart = cartService.removeProduct(cartId, productId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/public/carts/{cartId}/clear")
    public ResponseEntity<Object> clearCart(@PathVariable Long cartId) throws ItemNotFoundException {
        Cart cart = cartService.clearCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/public/carts/{cartId}/getTotal")
    public double getTotal(@PathVariable Long cartId) throws ItemNotFoundException{
        double total = cartService.getTotal(cartId);
        return total;
    }

    @GetMapping("/public/carts/{cartId}/itemCount")
    public int getItemCount(@PathVariable Long cartId) throws ItemNotFoundException{
        int itemCount = cartService.getItemCount(cartId);
        return itemCount;
    }

    @GetMapping("/public/carts/{cartId}/cartProducts")
    public ResponseEntity<List<CartProduct>> getCartProducts(@PathVariable Long cartId) throws ItemNotFoundException{
        List<CartProduct> cartProducts = cartService.getCartProducts(cartId);
        return ResponseEntity.ok(cartProducts);
    }
}
