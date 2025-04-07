package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.JWTResponse;
import com.mmf.financeflow.dto.LoginRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.AppUser;
import com.mmf.financeflow.entity.UserRole;
import com.mmf.financeflow.service.AppUserService;
import com.mmf.financeflow.util.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
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
            UserDetails userDetails = appUserService.loadUserByUsername(loginRequest.getUsername());
            Set<UserRole> roles = userDetails.getAuthorities().stream()
                    .map(auth -> UserRole.valueOf(auth.getAuthority()))
                    .collect(Collectors.toSet());

            String token = JWTUtil.generateToken(userDetails.getUsername(), roles);
            return ResponseEntity.ok(new JWTResponse(token, userDetails.getUsername(), roles));
        } else {
            return ResponseEntity.status(403).body(new JWTResponse("", loginRequest.getUsername(), Set.of()));
        }
    }
}
