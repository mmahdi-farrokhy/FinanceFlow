package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.AccountRequest;
import com.mmf.financeflow.dto.BudgetRequest;
import com.mmf.financeflow.dto.ExpenseRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.*;
import com.mmf.financeflow.exception.DuplicatedAccountCategoryException;
import com.mmf.financeflow.exception.InsufficientBalanceException;
import com.mmf.financeflow.exception.InvalidAmountException;
import com.mmf.financeflow.exception.MismatchedCategoryException;
import com.mmf.financeflow.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerClient(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Client registeredClient = new Client(username, encodedPassword);
        clientRepository.save(registeredClient);
    }

    @Override
    public boolean existsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Account createAccount(AccountRequest request, String username) {
        Account account = new Account(request.getTitle(), request.getCategory());
        BudgetCategory accountCategory = account.getCategory();
        String accountTitle = account.getTitle();

        Client client = findClientByUsername(username);

        client.findAccountWithCategory(accountCategory)
                .ifPresent(acc -> {
                    throw new DuplicatedAccountCategoryException("Account from category " + accountCategory + " already exists!");
                });

        List<String> accountsTitles = client.getAccountsTitles();

        if (accountsTitles.contains(accountTitle)) {
            throw new DuplicatedAccountCategoryException("Account with title " + accountTitle + " already exists!");
        }

        client.addAccount(account);
        clientRepository.save(client);
        return account;
    }

    @Override
    public List<Account> getAccounts(String username) {
        return clientRepository.findAccountsByUsername(username);
    }

    @Override
    public Account getAccountByType(String username, BudgetCategory category) {
        return clientRepository.findAccountByUsernameAndCategory(username, category);
    }

    @Override
    public Client findClientByUsername(String username) {
        return clientRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }
}
