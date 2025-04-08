package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.JWTResponse;
import com.mmf.financeflow.dto.LoginRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class ClientController {
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (clientService.exitsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken.");
        }

        Optional<Client> createdUser = clientService.registerClient(registerRequest);

        if (createdUser.isPresent()) {
            return ResponseEntity.ok("User registered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not register the user.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginRequest loginRequest) {
        if (clientService.areLoginCredentialsValid(loginRequest)) {
            JWTResponse jwtResponse = clientService.generateJWTResponse(loginRequest.getUsername());
            return ResponseEntity.ok(jwtResponse);
        } else {
            JWTResponse invalidResponse = new JWTResponse("", loginRequest.getUsername(), Set.of());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidResponse);
        }
    }
}
