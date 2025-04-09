package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.*;
import com.mmf.financeflow.entity.*;
import com.mmf.financeflow.exception.InvalidAmountException;
import com.mmf.financeflow.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public boolean existsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    @Override
    public Income createIncome(IncomeRequest request, String username) {
        Income income = new Income(request.getAmount(), request.getDescription());
        Client client = findClientByUsername(username);

        if (income.getAmount() <= 0) {
            throw new InvalidAmountException("Income amount should be greater than 0!");
        }

        client.increaseUnallocatedBudget(income);
        client.addIncome(income);
        clientRepository.save(client);
        return income;
    }

    @Override
    public Expense createExpense(ExpenseRequest request, String username) {
        Expense expense = new Expense(request.getAmount(), request.getDescription(), request.getCategory());
        Client client = findClientByUsername(username);
        client.addExpense(expense);
        clientRepository.save(client);
        return expense;
    }

    @Override
    public Budget createBudget(BudgetRequest request, String username) {
        Budget budget = new Budget(request.getAmount(), request.getCategory());
        Client client = findClientByUsername(username);
        client.addBudget(budget);
        clientRepository.save(client);
        return budget;
    }

    @Override
    public Account createAccount(AccountRequest request, String username) {
        Account account = new Account(request.getTitle(), request.getCategory());
        Client client = findClientByUsername(username);
        client.addAccount(account);
        clientRepository.save(client);
        return account;
    }

    private Client findClientByUsername(String username) {
        return clientRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }
}
