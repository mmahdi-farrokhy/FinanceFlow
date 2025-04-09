package com.mmf.financeflow.security;

import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientDetailsService implements UserDetailsService {
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));

        return new User(
                client.getUsername(),
                client.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
