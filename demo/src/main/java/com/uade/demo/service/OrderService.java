package com.uade.demo.service;

import java.util.List;

import com.uade.demo.entity.dto.OrderDTO;
import com.uade.demo.exceptions.ItemNotFoundException;

public interface OrderService {
    List<OrderDTO> getOrders();
    List<OrderDTO> getOrdersById(Long userId);
    OrderDTO placeOrder(Long cartId) throws ItemNotFoundException;
}
