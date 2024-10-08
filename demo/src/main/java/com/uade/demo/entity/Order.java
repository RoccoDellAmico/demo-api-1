package com.uade.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderProduct> products = new ArrayList<>(); // producto : talle

    private double total;

    //private LocalDate date;

    //private OrderStatus status;

    public Order(){}

    public Order(User user, double total){
        this.user = user;
        this.total = total;
    }

    public void addOrderProduct(OrderProduct orderProduct){
        this.products.add(orderProduct);
    }
}
