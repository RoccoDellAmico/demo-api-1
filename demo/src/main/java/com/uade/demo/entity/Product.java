package com.uade.demo.entity;

import java.util.List;
import jakarta.persistence.ElementCollection;
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

    @ElementCollection
    private List<String> photos;

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

    public Product() {}

    public Product(String description, double price, String club, String league, List<String> photos) {
        this.description = description;
        this.price = price;
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
}
