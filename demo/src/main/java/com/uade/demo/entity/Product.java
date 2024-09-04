package com.uade.demo.entity;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double price;

    //private Map<String, Integer> stock; // talle : stock del talle

    private String size;

    private int stock;

    private String club;

    private String league;

    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )   
    private List<Category> categories;

    //@ManyToMany
    //@JoinColumn(name = "category_id")
    //private List<Category> categories;

    public Product(String description, double price, String club, String league){
        this.description = description;
        this.price = price;
        this.club = club;
        this.league = league;
    }
}
