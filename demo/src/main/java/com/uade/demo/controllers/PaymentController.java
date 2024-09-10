package com.uade.demo.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.uade.demo.entity.Payment;
import com.uade.demo.entity.dto.PaymentDTO;
import com.uade.demo.service.PaymentService;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping("/public/payments")
    public Payment createPayment(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setDate(paymentDTO.getDate());
        payment.setIdOrder(paymentDTO.getIdOrder());
        payment.setIdUser(paymentDTO.getIdUser());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setTotal(paymentDTO.getTotal());
        return paymentService.createPayment(payment);
    }

    @GetMapping("/admin/payments")
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }

    @GetMapping("/public/payments/{userId}")
    public List<Payment> getPaymentsByUser(@PathVariable Long userId) {
        return paymentService.getPaymentsByUser(userId);
    }
}