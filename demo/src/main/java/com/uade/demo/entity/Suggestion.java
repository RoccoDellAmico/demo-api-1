package com.uade.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String photo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Suggestion() {}

    public Suggestion(String description, String photo, User user) {
        this.description = description;
        this.photo = photo;
        this.user = user;
    }
}
