package com.uade.demo.entity.dto;

import java.util.List;

import com.uade.demo.entity.OrderProduct;

import lombok.Data;

@Data
public class OrderDTO {
    private Long orderId;
    private List<OrderProduct> orderProducts;
    private double total;
}
