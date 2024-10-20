package com.uade.demo.controllers.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uade.demo.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("role")
    private Role userRole;

    @JsonProperty("id")
    private Long userId;
}
