package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.*;
import com.mmf.financeflow.entity.*;

import java.util.List;

public interface ClientService {
    void registerClient(RegisterRequest registerRequest);

    boolean existsByUsername(String username);

    public Client findClientByUsername(String username);

    void save(Client client);

    Account createAccount(AccountRequest accountRequest, String username);

    List<Account> getAccounts(String username);

    Account getAccountByType(String username, BudgetCategory category);
}
