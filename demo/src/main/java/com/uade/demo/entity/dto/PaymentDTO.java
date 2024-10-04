package com.uade.demo.entity.dto;
import lombok.Data;

@Data
public class PaymentDTO {
    private Long idOrder;
    private Long idUser;
    private String paymentMethod;
    private double total;
}