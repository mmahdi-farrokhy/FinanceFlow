package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.ExpenseRequest;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.entity.Expense;

import java.util.List;

public interface ExpenseService {
    Expense createExpense(ExpenseRequest expenseRequest, String username);

    List<Expense> getExpenses(String username);

    List<Expense> getExpensesByCategory(String username, BudgetCategory category);
}
