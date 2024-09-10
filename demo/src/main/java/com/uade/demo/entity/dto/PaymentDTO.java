package com.uade.demo.entity.dto;
import lombok.Data;
import java.util.Date;

@Data
public class PaymentDTO {
    private Date date;
    private Long idOrder;
    private Long idUser;
    private String paymentMethod;
    private double total;
}