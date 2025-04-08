package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.JWTResponse;
import com.mmf.financeflow.dto.LoginRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.Client;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClientService {
    Client registerClient(RegisterRequest registerRequest);

    boolean exitsByUsername(String username);

    boolean areLoginCredentialsValid(LoginRequest loginRequest);

    UserDetails loadUserByUsername(String username);
}
