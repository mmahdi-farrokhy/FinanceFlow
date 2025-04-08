package com.mmf.financeflow.service;

import com.mmf.financeflow.entity.AppUser;
import com.mmf.financeflow.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));

        Set<SimpleGrantedAuthority> authorities = appUser.getRoles().stream()
                .map(UserRole::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                authorities);
    }
}
