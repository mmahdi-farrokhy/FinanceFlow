package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.*;
import com.mmf.financeflow.entity.*;

import java.util.List;

public interface ClientService {
    void registerClient(RegisterRequest registerRequest);

    boolean existsByUsername(String username);

    public Client findClientByUsername(String username);

    void save(Client client);

    Expense createExpense(ExpenseRequest expenseRequest, String username);

    List<Expense> getExpenses(String username);

    List<Expense> getExpensesByCategory(String username, BudgetCategory category);

    Budget createBudget(BudgetRequest budgetRequest, String username);

    List<Budget> getBudgets(String username);

    List<Budget> getBudgetsByCategory(String username, BudgetCategory category);

    Account createAccount(AccountRequest accountRequest, String username);

    List<Account> getAccounts(String username);

    Account getAccountByType(String username, BudgetCategory category);
}
