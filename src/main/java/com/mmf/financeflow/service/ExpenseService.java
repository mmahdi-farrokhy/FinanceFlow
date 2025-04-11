package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.ExpenseRequest;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.entity.Expense;

import java.util.List;

public interface ExpenseService {
    Expense create(ExpenseRequest expenseRequest, String username);

    List<Expense> findAll(String username);

    List<Expense> findByCategory(String username, BudgetCategory category);
}
