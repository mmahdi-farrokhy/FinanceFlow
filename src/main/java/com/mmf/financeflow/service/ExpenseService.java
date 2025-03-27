package com.mmf.financeflow.service;

import com.mmf.financeflow.entity.Expense;

import java.util.List;

public interface ExpenseService {
    Expense create(Expense expense);

    List<Expense> findAll();
}
