package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.*;
import com.mmf.financeflow.entity.*;

public interface ClientService {
    void registerClient(RegisterRequest registerRequest);

    boolean existsByUsername(String username);

    Income createIncome(IncomeRequest incomeRequest, String username);

    Expense createExpense(ExpenseRequest expenseRequest, String username);

    Budget createBudget(BudgetRequest budgetRequest);

    Account createAccount(AccountRequest accountRequest);
}
