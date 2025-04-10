package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.AccountRequest;
import com.mmf.financeflow.entity.Account;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.exception.DuplicatedAccountException;
import com.mmf.financeflow.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private ClientService clientService;
    private AccountRepository accountRepository;

    @Override
    public Account create(AccountRequest request, String username) {
        String accountTitle = request.getTitle();
        BudgetCategory accountCategory = request.getCategory();
        Account account = new Account(accountTitle, accountCategory);

        Client client = clientService.findByUsername(username);

        client.findAccountWithCategory(accountCategory)
                .ifPresent(acc -> {
                    throw new DuplicatedAccountException("Account from category " + accountCategory + " already exists!");
                });

        client.findAccountWithTitle(accountTitle)
                .ifPresent(acc -> {
                    throw new DuplicatedAccountException("Account with title " + accountTitle + " already exists!");
                });

        client.addAccount(account);
        clientService.update(client);
        return account;
    }

    @Override
    public List<Account> findAll(String username) {
        return accountRepository.findAccountsByUsername(username);
    }

    @Override
    public Account findByCategory(String username, BudgetCategory category) {
        return accountRepository.findAccountByUsernameAndCategory(username, category);
    }
}
