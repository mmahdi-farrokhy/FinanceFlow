package com.mmf.financeflow.dto;

import com.mmf.financeflow.entity.UserRole;
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
