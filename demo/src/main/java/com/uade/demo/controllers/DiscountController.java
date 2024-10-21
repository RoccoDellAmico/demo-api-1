package com.uade.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uade.demo.entity.Discount;
import com.uade.demo.entity.dto.DiscountRequest;
import com.uade.demo.entity.dto.UpdateDiscountRequest;
import com.uade.demo.service.DiscountService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<Discount> updateDiscount(@RequestBody UpdateDiscountRequest updateDiscountRequest){
        Discount discount = discountService.updateDiscount(updateDiscountRequest.getId(), 
            updateDiscountRequest.getCode(), updateDiscountRequest.getDescription(),
            updateDiscountRequest.getPercentage(), updateDiscountRequest.getFixedAmount(), 
            updateDiscountRequest.getStartDate(),
            updateDiscountRequest.getEndDate());
        return ResponseEntity.ok(discount);
    }
    
    @DeleteMapping("/admin/discounts/delete/{discountId}")
    public ResponseEntity<Object> deleteDiscount(@PathVariable Long discountId){
        discountService.deleteDiscount(discountId);
        return ResponseEntity.noContent().build();
    }
}
