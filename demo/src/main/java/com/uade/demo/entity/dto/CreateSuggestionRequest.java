package com.uade.demo.entity.dto;

import lombok.Data;

@Data
public class CreateSuggestionRequest {
    private String description;
    private String photo;
    private Long userId;
}
