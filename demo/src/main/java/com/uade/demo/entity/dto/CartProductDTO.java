package com.uade.demo.entity.dto;

import com.uade.demo.entity.Product;

import lombok.Data;

@Data
public class CartProductDTO {
    private Long id;
    private Long cartId;
    private Product product;
    private int quantity;
}  
