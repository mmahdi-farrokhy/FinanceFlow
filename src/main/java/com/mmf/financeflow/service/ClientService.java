package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.IncomeRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.entity.Income;

public interface ClientService {
    Client registerClient(RegisterRequest registerRequest);

    boolean existsByUsername(String username);

    Income createIncome(IncomeRequest incomeRequest);
}
