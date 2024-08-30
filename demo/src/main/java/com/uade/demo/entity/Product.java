package com.uade.demo.entity;

import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String description;
    @Column
    private double price;
    @Column
    private Map<String, Integer> stock; // talle : stock del talle
    @Column
    private String club;
    @Column
    private String league;

    public Product(String description, double price, String club, String league){
        this.description = description;
        this.price = price;
        this.club = club;
        this.league = league;
    }
}
