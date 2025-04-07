package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.JWTResponse;
import com.mmf.financeflow.dto.LoginRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.AppUser;
import com.mmf.financeflow.service.AppUserService;
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
public class AppUserController {
    private AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (appUserService.exitsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken.");
        }

        Optional<AppUser> createdUser = appUserService.registerAppUser(registerRequest);

        if (createdUser.isPresent()) {
            return ResponseEntity.ok("User registered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not register the user.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginRequest loginRequest) {
        if (appUserService.areLoginCredentialsValid(loginRequest)) {
            JWTResponse jwtResponse = appUserService.generateJWTResponse(loginRequest.getUsername());
            return ResponseEntity.ok(jwtResponse);
        } else {
            JWTResponse invalidResponse = new JWTResponse("", loginRequest.getUsername(), Set.of());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidResponse);
        }
    }
}
