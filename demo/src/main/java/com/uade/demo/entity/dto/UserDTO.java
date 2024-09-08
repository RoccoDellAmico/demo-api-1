package com.uade.demo.entity.dto;

import com.uade.demo.entity.Role;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}
