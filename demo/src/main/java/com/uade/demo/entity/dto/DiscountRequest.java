package com.uade.demo.entity.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DiscountRequest {
    private String code;
    private String description;
    private double percentage;
    private double fixedAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}