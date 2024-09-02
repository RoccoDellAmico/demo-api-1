package com.uade.demo.entity;

import lombok.Data;

@Data
public class CartProduct {
    private Cart cart;
    private Product product;
    private int quantity;
}
