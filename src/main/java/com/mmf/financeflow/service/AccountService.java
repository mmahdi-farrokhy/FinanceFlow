package com.mmf.financeflow.service;

import com.mmf.financeflow.entity.Account;

import java.util.List;

public interface AccountService {
    Account create(Account account);

    List<Account> findAll();
}
