package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.Client;

public interface ClientService {
    Client registerClient(RegisterRequest registerRequest);

    boolean exitsByUsername(String username);
}
