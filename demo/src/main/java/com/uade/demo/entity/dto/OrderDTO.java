package com.uade.demo.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
    private Long orderId;
    private List<OrderProductDTO> orderProducts;
    private double total;
}
