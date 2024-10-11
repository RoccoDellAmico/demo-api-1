package com.uade.demo.controllers;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.uade.demo.entity.Payment;
import com.uade.demo.entity.dto.PaymentDTO;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.service.PaymentService;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping("/user/payments/{orderId}/paymentMethod/{paymentMethod}")
    public Payment createPayment(@PathVariable Long orderId, @PathVariable String paymentMethod) 
        throws ItemNotFoundException {
        Payment payment = paymentService.createPayment(orderId, paymentMethod);
        return payment;
    }

    @GetMapping("/admin/payments")
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }

    @GetMapping("/user/payments/{userId}")
    public List<Payment> getPaymentsByUser(@PathVariable Long userId) {
        return paymentService.getPaymentsByUser(userId);
    }
}