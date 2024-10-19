package com.uade.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uade.demo.entity.Discount;
import com.uade.demo.entity.dto.DiscountRequest;
import com.uade.demo.service.DiscountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
@RequestMapping("/api")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @GetMapping("/admin/discounts")
    public ResponseEntity<List<Discount>> getDiscounts() {
        return ResponseEntity.ok(discountService.getDiscounts());
    }
    
    @PostMapping("/admin/discounts/create")
    public ResponseEntity<Discount> createDiscount(@RequestBody DiscountRequest discountRequest) {
        Discount discount = discountService.createDiscount(discountRequest.getCode(), discountRequest.getDescription(),
            discountRequest.getPercentage(), discountRequest.getFixedAmount(), discountRequest.getStartDate(),
            discountRequest.getEndDate());
        return ResponseEntity.ok(discount);
    }

    @PutMapping("/admin/discounts/update")
    public ResponseEntity<Discount> updateDiscount(@RequestBody DiscountRequest discountRequest){
        Discount discount = discountService.updateDiscount(discountRequest.getCode(), discountRequest.getDescription(),
            discountRequest.getPercentage(), discountRequest.getFixedAmount(), discountRequest.getStartDate(),
            discountRequest.getEndDate());
        return ResponseEntity.ok(discount);
    }
    

}
