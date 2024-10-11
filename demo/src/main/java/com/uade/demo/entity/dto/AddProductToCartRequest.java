package com.uade.demo.entity.dto;

import com.uade.demo.entity.Size;

import lombok.Data;

@Data   
public class AddProductToCartRequest {
    private Long cartId;
    private Size size;
    private Long productId;
    private int quantity;
}
