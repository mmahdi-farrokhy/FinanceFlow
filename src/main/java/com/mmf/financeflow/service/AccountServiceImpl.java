package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.AccountRequest;
import com.mmf.financeflow.entity.Account;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.exception.DuplicatedAccountCategoryException;
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

    public Account createAccount(AccountRequest request, String username) {
        Account account = new Account(request.getTitle(), request.getCategory());
        BudgetCategory accountCategory = account.getCategory();
        String accountTitle = account.getTitle();

        Client client = clientService.findByUsername(username);

        client.findAccountWithCategory(accountCategory)
                .ifPresent(acc -> {
                    throw new DuplicatedAccountCategoryException("Account from category " + accountCategory + " already exists!");
                });

        List<String> accountsTitles = client.getAccountsTitles();

        if (accountsTitles.contains(accountTitle)) {
            throw new DuplicatedAccountCategoryException("Account with title " + accountTitle + " already exists!");
        }

        client.addAccount(account);
        clientService.update(client);
        return account;
    }

    @Override
    public List<Account> getAccounts(String username) {
        return accountRepository.findAccountsByUsername(username);
    }

    @Override
    public Account getAccountByType(String username, BudgetCategory category) {
        return accountRepository.findAccountByUsernameAndCategory(username, category);
    }
}
