package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.AppUser;
import com.mmf.financeflow.entity.UserRole;
import com.mmf.financeflow.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public Optional<AppUser> createAppUser(RegisterRequest registerRequest) {
        AppUser appUser = new AppUser();
        appUser.setUsername(registerRequest.getUsername());
        appUser.setPassword(registerRequest.getPassword());
        appUser.setRoles(Set.of(UserRole.ROLE_USER));
        AppUser save = appUserRepository.save(appUser);
        return Optional.of(save);
    }
}
