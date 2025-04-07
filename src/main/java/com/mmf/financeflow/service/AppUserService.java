package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.LoginRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AppUserService {
    Optional<AppUser> registerAppUser(RegisterRequest registerRequest);

    boolean exitsByUsername(String username);

    boolean areLoginCredentialsValid(LoginRequest loginRequest);

    UserDetails loadUserByUsername(String username);
}
