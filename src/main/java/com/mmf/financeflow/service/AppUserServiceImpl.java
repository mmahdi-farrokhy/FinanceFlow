package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.JWTResponse;
import com.mmf.financeflow.dto.LoginRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.AppUser;
import com.mmf.financeflow.entity.UserRole;
import com.mmf.financeflow.repository.AppUserRepository;
import com.mmf.financeflow.util.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private AppUserRepository appUserRepository;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<AppUser> registerAppUser(RegisterRequest registerRequest) {
        AppUser registeredAppUser = new AppUser();
        registeredAppUser.setUsername(registerRequest.getUsername());
        registeredAppUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        registeredAppUser.setRoles(Set.of(UserRole.ROLE_USER));
        return Optional.of(appUserRepository.save(registeredAppUser));
    }

    @Override
    public boolean exitsByUsername(String username) {
        return appUserRepository.exitsByUsername(username);
    }

    @Override
    public boolean areLoginCredentialsValid(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            authenticationManager.authenticate(authenticationToken);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public JWTResponse generateJWTResponse(String username) {
        UserDetails userDetails = loadUserByUsername(username);

        Set<UserRole> roles =
                userDetails.getAuthorities().stream()
                        .map(auth -> UserRole.valueOf(auth.getAuthority()))
                        .collect(Collectors.toSet());

        String token = JWTUtil.generateToken(userDetails.getUsername(), roles);

        return new JWTResponse(token, userDetails.getUsername(), roles);
    }
}
