package com.uade.demo.entity.dto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDTO {
    private LocalDate date;
    private Long idOrder;
    private Long idUser;
    private String paymentMethod;
    private double total;
}