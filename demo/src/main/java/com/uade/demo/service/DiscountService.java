package com.uade.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.uade.demo.entity.Discount;

public interface DiscountService {
    boolean validatePromoCode(String code);
    List<Discount> getDiscounts();
    Discount createDiscount(String code, String description, double percentage, double fixedAmount, 
        LocalDateTime startDate, LocalDateTime endDate);
    Discount updateDiscount(String code, String description, double percentage, double fixedAmount, 
        LocalDateTime startDate, LocalDateTime endDate);
    Discount getDiscount(String code);
}
