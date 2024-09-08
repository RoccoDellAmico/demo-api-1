package com.uade.demo.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartDTO {
    private Long cartId;
    private List<CartProductDTO> cartProducts;
    private String userEmail;
    private boolean active;
}
