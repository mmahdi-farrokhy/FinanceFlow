package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.JWTResponse;
import com.mmf.financeflow.dto.LoginRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.AppUser;
import com.mmf.financeflow.entity.UserRole;
import com.mmf.financeflow.repository.AppUserRepository;
import com.mmf.financeflow.service.AuthService;
import com.mmf.financeflow.util.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (appUserRepository.exitsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(409).body("Username is already taken.");
        }

        Optional<AppUser> createdUser = authService.registerAppUser(registerRequest);

        if (createdUser.isPresent()) {
            return ResponseEntity.ok("User registered successfully.");
        } else {
            return ResponseEntity.status(400).body("Could not register the user.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        Set<UserRole> roles = userDetails.getAuthorities().stream()
                .map(auth -> UserRole.valueOf(auth.getAuthority()))
                .collect(Collectors.toSet());

        String token = JWTUtil.generateToken(userDetails.getUsername(), roles);
        return ResponseEntity.ok(new JWTResponse(token, userDetails.getUsername(), roles));
    }
}
