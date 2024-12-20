package com.uade.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.demo.controllers.YourCustomException;
import com.uade.demo.entity.Discount;
import com.uade.demo.repository.DiscountRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public boolean validatePromoCode(String code){
        Optional<Discount> discountOptional = discountRepository.findByCode(code) ;
        if(discountOptional.isPresent()){
            Discount discount = discountOptional.get();
            if(!isDiscountExpired(discount)){
                return true;
            }
        }
        return false;
    }

    public List<Discount> getDiscounts(){
        return discountRepository.findAll();
    }

    public Discount createDiscount(String code, String description, double percentage, double fixedAmount, 
        LocalDateTime startDate, LocalDateTime endDate){
            Optional<Discount> discountOptional = discountRepository.findByCode(code);
            if(discountOptional.isPresent()){
                throw new YourCustomException("Discount code already exists");
            }
            Discount discount = discountRepository.save(new Discount(code, description, percentage, fixedAmount, 
            startDate, endDate));
            return discount;
    }

    public Discount updateDiscount(Long id, String code, String description, double percentage, double fixedAmount, 
        LocalDateTime startDate, LocalDateTime endDate){
            Optional<Discount> discountOptional = discountRepository.findById(id);
            if(discountOptional.isPresent()){
                Discount discount = discountOptional.get();
                discount.setCode(code);
                discount.setDescription(description);
                discount.setPercentage(percentage);
                discount.setFixedAmount(fixedAmount);
                discount.setStartDate(startDate);
                discount.setEndDate(endDate);
                return discountRepository.save(discount);
            }
        return null;
    }

    public Discount getDiscount(String code){
        Optional<Discount> discountOptional = discountRepository.findByCode(code);
        if(discountOptional.isPresent()){
            Discount discount = discountOptional.get();
            if(!isDiscountExpired(discount)){
                return discount;
            }
        }
        return null;
    }

    public void deleteDiscount(Long discountId){
        Optional<Discount> discountOptional = discountRepository.findById(discountId);
        if(discountOptional.isPresent()){
            Discount discount = discountOptional.get();
            discountRepository.delete(discount);
        }
    }

    private boolean isDiscountExpired(Discount discount){
        return LocalDateTime.now().isAfter(discount.getEndDate());
    }
}
