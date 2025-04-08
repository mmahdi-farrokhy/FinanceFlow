package com.mmf.financeflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private Set<UserRole> roles;
}
