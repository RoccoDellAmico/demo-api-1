package com.uade.demo.entity.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private Long id;
    private String description;
    private double price;
    private String club;
    private String league;
}
