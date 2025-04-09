package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Client registerClient(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Client registeredClient = new Client(username, encodedPassword);
        return clientRepository.save(registeredClient);
    }

    @Override
    public boolean exitsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }
}
