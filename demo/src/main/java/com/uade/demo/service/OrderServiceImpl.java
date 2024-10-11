package com.uade.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.demo.controllers.YourCustomException;
import com.uade.demo.entity.Cart;
import com.uade.demo.entity.CartProduct;
import com.uade.demo.entity.Order;
import com.uade.demo.entity.OrderProduct;
import com.uade.demo.entity.dto.OrderDTO;
import com.uade.demo.entity.dto.OrderProductDTO;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.repository.CartRepository;
import com.uade.demo.repository.OrderRepository;
import com.uade.demo.repository.ProductRepository;

@Transactional
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    public OrderDTO mapToOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getId());
        orderDTO.setOrderProducts(mapToOrderProductDTO(order.getProducts()));
        orderDTO.setUserEmail(order.getUser().getEmail());
        orderDTO.setTotal(order.getTotal());
        return orderDTO;
    }

    public List<OrderProductDTO> mapToOrderProductDTO(List<OrderProduct> orderProducts){
        List<OrderProductDTO> orderProductDTOs = new ArrayList<>();
        for(OrderProduct orderProduct : orderProducts){
            OrderProductDTO orderProductDTO = new OrderProductDTO();
            orderProductDTO.setId(orderProduct.getId());
            orderProductDTO.setOrderId(orderProduct.getOrder().getId());
            orderProductDTO.setProduct(orderProduct.getProduct());
            orderProductDTO.setQuantity(orderProduct.getQuantity());
            orderProductDTO.setSize(orderProduct.getSize());
            orderProductDTOs.add(orderProductDTO);
        }
        return orderProductDTOs;
    }

    @Override
    public List<OrderDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for(Order order : orders){
            OrderDTO orderDTO = mapToOrderDTO(order);
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public OrderDTO placeOrder(Long cartId) throws ItemNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(()-> new ItemNotFoundException());
        Order order = createOrder(cart);
        List<OrderProduct> orderProducts = mapToOrderProduct(cart.getCartProducts(), order);
        for(OrderProduct orderProduct : orderProducts){
            if(orderProduct.getQuantity() > orderProduct.getProduct().getStockBySize(orderProduct.getSize())){
                throw new YourCustomException(orderProduct.getProduct().getDescription() + " sin stock disponible");
            }
            order.addOrderProduct(orderProduct);
            orderProduct.getProduct().updateProductStock(orderProduct.getSize(), 
                orderProduct.getProduct().getStockBySize(orderProduct.getSize())-orderProduct.getQuantity());
            productRepository.save(orderProduct.getProduct());
        }
        Order savedOrder = orderRepository.save(order);
        cart.changeState();
        cartRepository.save(cart);
        return mapToOrderDTO(savedOrder);
    }

    public Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setTotal(cart.getTotal());
        return order;
    }

    public List<OrderProduct> mapToOrderProduct(List<CartProduct> cartProducts, Order order){
        List<OrderProduct> orderProducts = new ArrayList<>();
        for(CartProduct cartProduct : cartProducts){
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(cartProduct.getProduct());
            orderProduct.setQuantity(cartProduct.getQuantity());
            orderProduct.setSize(cartProduct.getSize());
            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }

}
