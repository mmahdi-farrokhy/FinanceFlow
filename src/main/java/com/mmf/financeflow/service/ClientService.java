package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.*;
import com.mmf.financeflow.entity.*;

import java.util.List;

public interface ClientService {
    void registerClient(RegisterRequest registerRequest);

    boolean existsByUsername(String username);

    Income createIncome(IncomeRequest incomeRequest, String username);

    Expense createExpense(ExpenseRequest expenseRequest, String username);

    Budget createBudget(BudgetRequest budgetRequest, String username);

    Account createAccount(AccountRequest accountRequest, String username);

    List<Income> getIncomes(String username);

    List<Expense> getExpenses(String username);

    List<Budget> getBudgets(String username);

    List<Account> getAccounts(String username);

    List<Expense> getExpensesByCategory(String username, BudgetCategory category);
}
