package com.uade.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.demo.entity.dto.CartDTO;
import com.uade.demo.entity.dto.OrderDTO;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/orders")
    public ResponseEntity<List<OrderDTO>> getOrders(){
        List<OrderDTO> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/public/placeOrder/{cartId}")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable Long cartId) throws ItemNotFoundException{
        OrderDTO order = orderService.placeOrder(cartId);
        return ResponseEntity.ok(order);
    }
    
    
}