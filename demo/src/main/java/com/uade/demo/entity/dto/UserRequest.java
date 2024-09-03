package com.uade.demo.entity.dto;

import com.uade.demo.entity.Role;
import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String mail;
}
