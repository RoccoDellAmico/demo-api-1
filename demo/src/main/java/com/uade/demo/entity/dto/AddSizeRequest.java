package com.uade.demo.entity.dto;

import com.uade.demo.entity.Size;

import lombok.Data;

@Data
public class AddSizeRequest {
    private Long productId;
    private Size size;
    private int stock;
}
