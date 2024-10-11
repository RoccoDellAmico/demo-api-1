package com.uade.demo.entity;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double price;

    @ElementCollection
    @CollectionTable(name = "product_stock")
    @MapKeyColumn(name = "size")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "stock")
    private Map<Size, Integer> productStock = new HashMap<>();

    private String club;

    private String league;

    @ElementCollection
    private List<String> photos;

    @Enumerated(EnumType.STRING)
    private ClientCategory clientCategory;

    @Enumerated(EnumType.STRING)
    private TypeOfProduct typeOfProduct;

    private int year;

    public Product(String description, double price, Map<Size, Integer> productStock,
        String club, String league, List<String> photos, ClientCategory clientCategory, TypeOfProduct typeOfProduct, 
        int year) {
        this.description = description;
        this.price = price;
        this.productStock = productStock;
        this.club = club;
        this.league = league;
        this.photos = photos;
        this.clientCategory = clientCategory;
        this.typeOfProduct = typeOfProduct;
        this.year = year;
    }

    public void updateProductStock(Size size, int stock){
        if(productStock.containsKey(size)){
            productStock.put(size, stock);
        }
    }

    public int getStockBySize(Size size){
        return productStock.getOrDefault(size, 0);
    }

    public void addProductSize(Size size, int stock){
        if(!productStock.containsKey(size)){
            productStock.put(size, stock);
        }
    }

    public Product(){}
}
