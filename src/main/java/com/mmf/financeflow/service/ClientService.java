package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.BudgetRequest;
import com.mmf.financeflow.dto.ExpenseRequest;
import com.mmf.financeflow.dto.IncomeRequest;
import com.mmf.financeflow.dto.RegisterRequest;
import com.mmf.financeflow.entity.Budget;
import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.entity.Expense;
import com.mmf.financeflow.entity.Income;

public interface ClientService {
    Client registerClient(RegisterRequest registerRequest);

    boolean existsByUsername(String username);

    Income createIncome(IncomeRequest incomeRequest);

    Expense createExpense(ExpenseRequest expenseRequest);

    Budget createBudget(BudgetRequest budgetRequest);
}
