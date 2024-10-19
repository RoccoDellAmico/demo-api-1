package com.uade.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.demo.entity.Discount;
import com.uade.demo.repository.DiscountRepository;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public Discount validatePromoCode(String code){
        Optional<Discount> discountOptional = discountRepository.findByCode(code) ;
        if(discountOptional.isPresent()){
            Discount discount = discountOptional.get();
            if(!isDiscountExpired(discount)){
                return discount;
            }
        }
        return null;

    }

    @Override
    public Discount validatePaymentMethodDiscount(String paymentMethod) {
        Optional<Discount> discountOptional = discountRepository.findByPaymentMethod(paymentMethod);
        if(discountOptional.isPresent()){
            Discount discount = discountOptional.get();
            if(!isDiscountExpired(discount)){
                return discount;
            }
        }
        return null;
    }

    private boolean isDiscountExpired(Discount discount){
        return LocalDateTime.now().isAfter(discount.getEndDate());
    }
}
