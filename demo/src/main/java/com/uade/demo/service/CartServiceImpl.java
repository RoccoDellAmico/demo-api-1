package com.uade.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.demo.entity.Product;
import com.uade.demo.entity.User;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.repository.CartRepository;
import com.uade.demo.repository.ProductRepository;
import com.uade.demo.repository.UserRepository;
import com.uade.demo.entity.Cart;
import com.uade.demo.entity.CartProduct;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart addProduct(Long cartId, Long productId, int quantity) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ItemNotFoundException());
        boolean hasProduct = cart.hasProduct(productId);
        if(!hasProduct){
            CartProduct cartProduct = new CartProduct();
            cartProduct.setCart(cart);
            cartProduct.setProduct(product);
            cartProduct.setQuantity(quantity);
            cart.addProduct(cartProduct);
            cartRepository.save(cart);
            return cart;
        }
        return null;
    }

    @Override
    public Cart removeProduct(Long cartId, Long productId) throws ItemNotFoundException{
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ItemNotFoundException());
        boolean hasProduct = cart.hasProduct(productId);
        if(hasProduct){
            cart.removeProduct(product);
            cartRepository.save(cart);
            return cart;
        }
        return null;
    }

    @Override
    public Cart updateProductQuantity(Long cartId, Long productId, int newQuantity) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        boolean hasProduct = cart.hasProduct(productId);
        if(hasProduct){
            cart.updateProductQuantity(productId, newQuantity);
            cartRepository.save(cart);
            return cart;
        }
        return null;
    }

    @Override
    public Cart addOneProduct(Long cartId, Long productId) throws ItemNotFoundException{
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        boolean hasProduct = cart.hasProduct(productId);
        if(hasProduct){
            cart.updateProductQuantity(productId, cart.getCartProductQuantity(productId) + 1);
            cartRepository.save(cart);
            return cart;
        }
        return null;
    }

    @Override
    public Cart substractOneProduct(Long cartId, Long productId) throws ItemNotFoundException{
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        boolean hasProduct = cart.hasProduct(productId);
        if(hasProduct){
            cart.updateProductQuantity(productId, cart.getCartProductQuantity(productId) - 1);
            cartRepository.save(cart);
            if(cart.getCartProductQuantity(productId) == 0)
                removeProduct(cartId, productId);
                return cart;
        }
        return null;
    }

    @Override
    public Cart clearCart(Long cartId) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        cart.clearCart();
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public List<CartProduct> getCartProducts(Long cartId) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        return cart.getCartProducts();
    }

    @Override
    public double getTotal(Long cartId) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        return cart.getTotal();
    }

    @Override
    public int getItemCount(Long cartId) throws ItemNotFoundException{
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        return cart.getItemCount();
    }  

    @Override
    public List<Cart> getCarts(){
        return cartRepository.findAll();
    }

    @Override
    public List<Cart> getCartsByUser(Long userId){
        List<Cart> carts = cartRepository.findCartByUser(userId);
        return carts;
    }

    @Override
    public Cart createCart(String email) throws ItemNotFoundException{
        User user = userRepository.findByEmail(email);
        return cartRepository.save(new Cart(user));
    }
}

