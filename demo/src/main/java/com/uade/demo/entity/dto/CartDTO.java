package com.uade.demo.entity.dto;

import java.util.List;

import lombok.Data;

import com.uade.demo.entity.CartProduct;

@Data
public class CartDTO {
    private Long cartId;
    private List<CartProduct> cartProducts;
}
