package com.uade.demo.entity.dto;

import java.util.List;
import java.util.Map;
import com.uade.demo.entity.Size;

import lombok.Data;

@Data
public class ProductRequest {
    private Long id;
    private String description;
    private double price;
    private Map<Size, Integer> productStock;
    private String club;
    private String league;
    private List<String> photos;
}
