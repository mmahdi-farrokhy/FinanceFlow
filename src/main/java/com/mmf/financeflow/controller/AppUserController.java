package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.JWTResponse;
import com.mmf.financeflow.dto.LoginRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.AppUser;
import com.mmf.financeflow.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (appUserService.exitsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(409).body("Username is already taken.");
        }

        Optional<AppUser> createdUser = appUserService.registerAppUser(registerRequest);

        if (createdUser.isPresent()) {
            return ResponseEntity.ok("User registered successfully.");
        } else {
            return ResponseEntity.status(400).body("Could not register the user.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginRequest loginRequest) {
        if (appUserService.areLoginCredentialsValid(loginRequest)) {
            JWTResponse jwtResponse = appUserService.generateJWTResponse(loginRequest.getUsername());
            return ResponseEntity.ok(jwtResponse);
        } else {
            return ResponseEntity.status(403).body(new JWTResponse("", loginRequest.getUsername(), Set.of()));
        }
    }
}
