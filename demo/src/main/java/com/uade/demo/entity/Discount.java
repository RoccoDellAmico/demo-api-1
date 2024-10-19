package com.uade.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private String code;
    private String description;
    private double percentage;
    private double fixedAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Discount(String code, String description, double percentage, double fixedAmount, 
        LocalDateTime startDate, LocalDateTime endDate){
        this.code = code;
        this.description = description;
        this.percentage = percentage;
        this.fixedAmount = fixedAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
