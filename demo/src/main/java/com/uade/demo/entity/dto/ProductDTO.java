package com.uade.demo.entity.dto;

import java.util.List;
import java.util.Map;

import com.uade.demo.entity.Size;
import com.uade.demo.entity.TypeOfProduct;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String description;
    private double price;
    private String league;
    private List<String> photos;
    private String category;
    private TypeOfProduct typeOfProduct;
    private Map<Size, Integer> productStock;
}
