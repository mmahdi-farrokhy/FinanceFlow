package com.mmf.financeflow.dto;

import com.mmf.financeflow.entity.UserRole;
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
