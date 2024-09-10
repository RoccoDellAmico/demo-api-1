package com.uade.demo.service;

import java.util.List;

import com.uade.demo.entity.Payment;
import com.uade.demo.exceptions.ItemNotFoundException;

public interface PaymentService {
    Payment createPayment(Long orderId, String paymentMethod) throws ItemNotFoundException;
    List<Payment> getPayments();
    List<Payment> getPaymentsByUser(Long UserId);
}