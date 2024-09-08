package com.uade.demo.entity.dto;

import com.uade.demo.entity.Product;

import lombok.Data;

@Data
public class OrderProductDTO {
    private Long id;
    private Long orderId;
    private Product product;
    private int quantity;
}
