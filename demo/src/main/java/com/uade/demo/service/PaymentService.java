package com.uade.demo.service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.uade.demo.entity.Payment;

public interface PaymentService {
    Payment createPayment(Payment payment);
    List<Payment> getPayments();
    List<Payment> getPaymentsByUser(Long UserId);
}