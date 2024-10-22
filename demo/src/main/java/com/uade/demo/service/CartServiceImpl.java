package com.uade.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.demo.entity.Product;
import com.uade.demo.entity.Size;
import com.uade.demo.entity.User;
import com.uade.demo.entity.dto.CartDTO;
import com.uade.demo.entity.dto.CartProductDTO;
import com.uade.demo.exceptions.InvalidItemCountException;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.repository.CartRepository;
import com.uade.demo.repository.ProductRepository;
import com.uade.demo.repository.UserRepository;
import com.uade.demo.controllers.YourCustomException;
import com.uade.demo.entity.Cart;
import com.uade.demo.entity.CartProduct;
import com.uade.demo.entity.Discount;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiscountService discountService;

    public CartDTO mapToCartDTO(Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());   
        cartDTO.setCartProducts(mapToCartProductDTO(cart.getCartProducts()));
        cartDTO.setUserEmail(cart.getUser().getEmail());
        cartDTO.setActive(cart.isActive()); 
        cartDTO.setDiscountCode(cart.getDiscountCode());
        return cartDTO;
    }

    public List<CartProductDTO> mapToCartProductDTO(List<CartProduct> cartProducts){
        List<CartProductDTO> cartProductsDTO = new ArrayList<>();
        for(CartProduct cartProduct : cartProducts){
            CartProductDTO cartProductDTO = new CartProductDTO();
            cartProductDTO.setId(cartProduct.getCartProductId());
            cartProductDTO.setCartId(cartProduct.getCart().getCartId());
            cartProductDTO.setProduct(cartProduct.getProduct());
            cartProductDTO.setQuantity(cartProduct.getQuantity());
            cartProductDTO.setSize(cartProduct.getSize());
            cartProductsDTO.add(cartProductDTO);
        }
        return cartProductsDTO;
    }

    @Override
    public CartDTO addProduct(Long cartId, Long productId, Size size, int quantity) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new ItemNotFoundException());
        boolean hasProduct = cart.hasProduct(productId, size);
        if(!hasProduct){
            CartProduct cartProduct = new CartProduct();
            cartProduct.setCart(cart);
            cartProduct.setProduct(product);
            if(quantity > product.getStockBySize(size)){
                throw new YourCustomException(product.getDescription() + " sin stock disponible");
            }
            if(quantity <= 0){
                throw new YourCustomException("La cantidad debe ser positiva");
            }
            cartProduct.setSize(size);
            cartProduct.setQuantity(quantity);
            cart.addProduct(cartProduct);
            cartRepository.save(cart);
            return mapToCartDTO(cart);
        }
        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO removeProduct(Long cartId, Long cartProductId) throws ItemNotFoundException{
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        boolean hasCartProduct = cart.hasCartProduct(cartProductId);
        if(hasCartProduct){
            cart.removeCartProduct(cartProductId);
            cartRepository.save(cart);
            return mapToCartDTO(cart);
        }
        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO updateProductQuantity(Long cartId, Long cartProductId, int newQuantity) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        boolean hasProduct = cart.hasCartProduct(cartProductId);
        if(hasProduct){
            Product product = cart.getProductByCartProductId(cartProductId);
            CartProduct cartProduct = cart.getCartProductByCartProductId(cartProductId);
            if(newQuantity == 0){
                removeProduct(cartId, cartProductId);
                return mapToCartDTO(cart);
            }
            if(newQuantity > product.getStockBySize(cartProduct.getSize())){
                throw new YourCustomException(product.getDescription() + " sin stock disponible");
            }
            cart.updateProductQuantity(product.getId(), cartProduct.getSize(), newQuantity);
            cartRepository.save(cart);
            return mapToCartDTO(cart);
        }
        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO addOneProduct(Long cartId, Size size,Long productId) throws ItemNotFoundException{
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        boolean hasProduct = cart.hasProduct(productId, size);
        if(hasProduct){
            Product product = productRepository.findById(productId).orElseThrow(
                () -> new ItemNotFoundException());
            if(cart.getCartProductQuantity(productId) + 1 > product.getStockBySize(size)){
                throw new YourCustomException(product.getDescription() + " sin stock disponible");
            }
            cart.updateProductQuantity(productId, size, cart.getCartProductQuantity(productId) + 1);
            cartRepository.save(cart);
            return mapToCartDTO(cart);
        }
        return mapToCartDTO(cart);
    }

    // no usar
    @Override
    public CartDTO substractOneProduct(Long cartId, Long productId, Size size) throws ItemNotFoundException{
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        boolean hasProduct = cart.hasProduct(productId, size);
        if(hasProduct){
            cart.updateProductQuantity(productId, size, cart.getCartProductQuantity(productId) - 1);
            cartRepository.save(cart);
            if(cart.getCartProductQuantity(productId) == 0){
                removeProduct(cartId, productId);
                return mapToCartDTO(cart);
            }
        }
        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO clearCart(Long cartId) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        cart.clearCart();
        cart.setDiscountCode(null);
        cartRepository.save(cart);
        return mapToCartDTO(cart);
    }

    @Override
    public List<CartProductDTO> getCartProducts(Long cartId) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        return mapToCartProductDTO(cart.getCartProducts());
    }

    @Override
    public double getTotal(Long cartId) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        if(cart.getDiscountCode() != null){
            Discount discount = discountService.getDiscount(cart.getDiscountCode());
            if(discount.getFixedAmount() == 0){
                return cart.getTotal() * discount.getPercentage();
            }
            else{
                return cart.getTotal() - discount.getFixedAmount();
            }
        }
        return cart.getTotal();
    }

    @Override
    public int getItemCount(Long cartId) throws ItemNotFoundException{
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        return cart.getItemCount();
    }  

    @Override
    public List<CartDTO> getCarts(){
        List<Cart> carts = cartRepository.findAll();
        List<CartDTO> cartDTOs = new ArrayList<>();
        for(Cart cart : carts){
            CartDTO cartDTO = mapToCartDTO(cart);
            cartDTOs.add(cartDTO);
        }
        return cartDTOs;
    }

    @Override
    public CartDTO getCartsByUser(Long userId){
        Cart cart = cartRepository.findCartByUser(userId);
        return mapToCartDTO(cart);
    }

    /*@Transactional(rollbackFor = Throwable.class)
    @Override
    public CartDTO createCart(String email) throws ItemNotFoundException{
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ItemNotFoundException());
        Cart cart = cartRepository.findCartByUser(user.getId());
        if(cart == null){
            Cart newCart = cartRepository.save(new Cart(user));
            return mapToCartDTO(newCart);
        }
        cart.clearCart();
        cart.changeState();
        return mapToCartDTO(cart);
    }*/

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public CartDTO createCart(Long userId) throws ItemNotFoundException, InvalidItemCountException {
        Optional<User> userOptional = userRepository.findById(userId);
        Cart cart = cartRepository.findCartByUser(userId);

        if (!userOptional.isPresent()) {
            throw new ItemNotFoundException();
        }

        if (cart == null) {
            // Si no existe un carrito, creamos uno nuevo
            User user = userOptional.get();
            Cart newCart = cartRepository.save(new Cart(user));    
            return mapToCartDTO(newCart);
        } else {
            // Limpiar el carrito existente
            cart.clearCart();
            cart.changeState();
            cart.setDiscountCode(null);
            return mapToCartDTO(cart);
        }
    }

    @Override
    public CartDTO getCartById(Long cartId) throws ItemNotFoundException{
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO addDiscountCode(String discountCode, Long cartId) throws ItemNotFoundException{
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(() -> new ItemNotFoundException());
        boolean isDiscountCodeValid = discountService.validatePromoCode(discountCode);
        if(isDiscountCodeValid){
            cart.setDiscountCode(discountCode);
            cartRepository.save(cart);
            return mapToCartDTO(cart);
        } 
        else {
            return mapToCartDTO(cart);
        }  
    }
}

