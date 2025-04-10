package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.Client;

public interface ClientService {
    void register(RegisterRequest registerRequest);

    boolean existsByUsername(String username);

    Client findByUsername(String username);

    void update(Client client);
}
