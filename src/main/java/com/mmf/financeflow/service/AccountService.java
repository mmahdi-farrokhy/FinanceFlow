package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.AccountRequest;
import com.mmf.financeflow.entity.Account;
import com.mmf.financeflow.entity.BudgetCategory;

import java.util.List;

public interface AccountService {
    Account create(AccountRequest accountRequest, String username);

    List<Account> findAll(String username);

    Account findByCategory(String username, BudgetCategory category);
}
