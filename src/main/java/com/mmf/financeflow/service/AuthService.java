package com.mmf.financeflow.service;

import com.mmf.financeflow.controller.AuthController;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.AppUser;

import java.util.Optional;

public interface AuthService {
    Optional<AppUser> createAppUser(RegisterRequest registerRequest);
}
