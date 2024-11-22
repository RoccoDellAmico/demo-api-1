package com.uade.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuggestionDTO {
    private Long id;
    private String description;
    private String photo;
    private Long userId;
    private String userEmail;
}
