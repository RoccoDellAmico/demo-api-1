package com.uade.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.demo.entity.Cart;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.service.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("carts")
public class CartController {
    //implementar crear carrito, metodos controller
    //implementar cartDTO
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<Cart>> getCarts(){
        List<Cart> carts = cartService.getCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/carts/{userId}")
    public ResponseEntity<List<Cart>> getCartsByUser(@PathVariable Long userId){
        List<Cart> carts = cartService.getCartsByUser(userId);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/carts/{email}")
    public ResponseEntity<Object> createCart(@PathVariable String email) throws ItemNotFoundException {
        Cart cart = cartService.createCart(email);
        return ResponseEntity.created(URI.create("cart" + cart.getCartId())).body(cart);
    }
    
}
