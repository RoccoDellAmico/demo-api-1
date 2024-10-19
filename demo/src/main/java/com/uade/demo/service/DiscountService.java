package com.uade.demo.service;

import com.uade.demo.entity.Discount;

public interface DiscountService {
    Discount validatePromoCode(String code);
    Discount validatePaymentMethodDiscount(String paymentMethod);
}
