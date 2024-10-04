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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyColumn;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double price;

    /*@Enumerated(EnumType.STRING)
    private Size size;

    private int stock;*/

    @ElementCollection
    @CollectionTable(name = "product_stock")
    @MapKeyColumn(name = "size")
    @Column(name = "stock")
    private Map<Size, Integer> productStock = new HashMap<>();

    private String club;

    private String league;

    @ElementCollection
    private List<String> photos;

    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )   
    private List<Category> categories;

    public Product(String description, double price, Map<Size, Integer> productStock,
        String club, String league, List<String> photos) {
        this.description = description;
        this.price = price;
        this.productStock = productStock;
        this.club = club;
        this.league = league;
        this.photos = photos;
    }

    public void addProductCategory(Category category) {
        categories.add(category);
    }

    public void removeProductCategory(Category category) {
        categories.remove(category);
    }

    public void updateProductStock(Size size, int stock){
        if(productStock.containsKey(size)){
            productStock.put(size, stock);
        }
    }

    public int getStockBySize(Size size){
        if(productStock.containsKey(size))
            return productStock.get(size);
        else{
            return -1;
        }
    }

    public void addProductSize(Size size, int stock){
        if(!productStock.containsKey(size)){
            productStock.put(size, stock);
        }
    }

    public Product(){}
}
