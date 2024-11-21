package com.uade.demo.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String description;
    private double price;
    private String league;
    private List<String> photos;
    private String category;
}
