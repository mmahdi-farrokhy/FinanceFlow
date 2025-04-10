package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.AccountRequest;
import com.mmf.financeflow.entity.Account;
import com.mmf.financeflow.entity.BudgetCategory;

import java.util.List;

public interface AccountService {
    Account createAccount(AccountRequest accountRequest, String username);

    List<Account> getAccounts(String username);

    Account getAccountByType(String username, BudgetCategory category);
}
