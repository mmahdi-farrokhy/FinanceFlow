package com.mmf.financeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class JWTResponse {
    private String token;
    private String username;
    private Set<UserRole> roles;
}
