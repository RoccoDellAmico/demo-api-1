package com.uade.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.demo.entity.Order;
import com.uade.demo.entity.Payment;
import com.uade.demo.exceptions.ItemNotFoundException;
import com.uade.demo.repository.OrderRepository;
import com.uade.demo.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment createPayment(Long orderId, String paymentMethod) throws ItemNotFoundException{
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ItemNotFoundException());
        Payment payment = new Payment();
        payment.setIdOrder(orderId);
        payment.setIdUser(order.getUser().getId());
        payment.setDate(LocalDate.now());
        payment.setTotal(order.getTotal());
        payment.setPaymentMethod(paymentMethod);
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
