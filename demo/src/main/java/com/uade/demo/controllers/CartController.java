package com.uade.demo.controllers;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.demo.entity.Size;
import com.uade.demo.entity.dto.AddProductToCartRequest;
import com.uade.demo.entity.dto.CartDTO;
import com.uade.demo.entity.dto.CartProductDTO;
import com.uade.demo.entity.dto.CreateCartRequest;
import com.uade.demo.exceptions.InvalidItemCountException;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.service.CartService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/admin/carts")
    public ResponseEntity<List<CartDTO>> getCarts(){
        List<CartDTO> carts = cartService.getCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/admin/carts/{userId}")
    public ResponseEntity<CartDTO> getCartsByUser(@PathVariable Long userId){
        CartDTO cart = cartService.getCartsByUser(userId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/user/carts/{userId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long userId) throws ItemNotFoundException{
        //CartDTO cart = cartService.getCartById(cartId);
        CartDTO cart = cartService.getCartsByUser(userId);
        return ResponseEntity.ok(cart);
    }
    

    /*@PostMapping("/user/carts/{email}")
    public ResponseEntity<Object> createCart(@PathVariable String email) throws ItemNotFoundException {
        CartDTO cart = cartService.createCart(email);
        return ResponseEntity.created(URI.create("cart" + cart.getCartId())).body(cart);
    }*/

@PostMapping("/user/carts/create")
public ResponseEntity<Object> createCart(@RequestBody CreateCartRequest createCartRequest) 
    throws ItemNotFoundException {
    
    Long userId = createCartRequest.getUserId();
    try {
        // Crear carrito solo si el número de ítems es permitido
        CartDTO createdCart = cartService.createCart(userId);
        return ResponseEntity.ok(createdCart);
    } catch (InvalidItemCountException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }
}
    
    
    @PutMapping("/user/carts")
    public ResponseEntity<CartDTO> addProductToCart(@RequestBody AddProductToCartRequest addProductToCartRequest)
        throws ItemNotFoundException {
        CartDTO cart = cartService.addProduct(addProductToCartRequest.getCartId(), 
        addProductToCartRequest.getProductId(), addProductToCartRequest.getSize(), 
        addProductToCartRequest.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/user/carts/{cartId}/products/{productId}/{size}/addOne")
    public ResponseEntity<Object> addOneProduct(@PathVariable Long cartId, 
        @PathVariable Size size, @PathVariable Long productId) throws ItemNotFoundException {
        CartDTO cart = cartService.addOneProduct(cartId, size, productId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/user/carts/{cartId}/products/{productId}/{size}/substractOne")
    public ResponseEntity<Object> substractOneProduct(@PathVariable Long cartId, @PathVariable Long productId,
        @PathVariable Size size) 
        throws ItemNotFoundException {
        CartDTO cart = cartService.substractOneProduct(cartId, productId, size);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/user/carts/{cartId}/products/{productId}/size/{size}/quantity/{newQuantity}/update")
    public ResponseEntity<Object> updateProductQuantity(@PathVariable Long cartId, @PathVariable Size size,
        @PathVariable Long productId, @PathVariable int newQuantity) throws ItemNotFoundException {
        CartDTO cart = cartService.updateProductQuantity(cartId, size, productId, newQuantity);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/user/carts/{cartId}/products/{cartProductId}/remove")
    public ResponseEntity<Object> removeProduct(@PathVariable Long cartId, @PathVariable Long cartProductId,) 
        throws ItemNotFoundException {
        CartDTO cart = cartService.removeProduct(cartId, cartProductId );
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/user/carts/{cartId}/clear")
    public ResponseEntity<Object> clearCart(@PathVariable Long cartId) throws ItemNotFoundException {
        CartDTO cart = cartService.clearCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/user/carts/{cartId}/getTotal")
    public ResponseEntity<Double> getTotal(@PathVariable Long cartId) throws ItemNotFoundException{
        double total = cartService.getTotal(cartId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/user/carts/{cartId}/itemCount")
    public ResponseEntity<Integer> getItemCount(@PathVariable Long cartId) throws ItemNotFoundException{
        int itemCount = cartService.getItemCount(cartId);
        return ResponseEntity.ok(itemCount);
    }

    @GetMapping("/user/carts/{cartId}/cartProducts")
    public ResponseEntity<List<CartProductDTO>> getCartProducts(@PathVariable Long cartId) throws ItemNotFoundException{
        List<CartProductDTO> cartProducts = cartService.getCartProducts(cartId);
        return ResponseEntity.ok(cartProducts);
    }

    @PutMapping("/user/carts/add-discount/{discountCode}/cart/{cartId}")
    public ResponseEntity<CartDTO> addDiscountCode(@PathVariable String discountCode, @PathVariable Long cartId) 
        throws ItemNotFoundException {
        CartDTO cartDTO = cartService.addDiscountCode(discountCode, cartId); 
        return ResponseEntity.ok(cartDTO);
    }
}
