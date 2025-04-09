package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (clientService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken.");
        }

        clientService.registerClient(registerRequest);
        return ResponseEntity.ok("User registered successfully.");
    }
}
