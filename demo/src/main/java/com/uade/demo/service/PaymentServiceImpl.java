package com.uade.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.demo.entity.Payment;
import com.uade.demo.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> getPaymentsByUser(Long UserId) {
        return paymentRepository.findByUserId(UserId);
    }

}
