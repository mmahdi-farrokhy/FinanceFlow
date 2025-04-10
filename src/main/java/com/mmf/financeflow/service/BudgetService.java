package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.BudgetRequest;
import com.mmf.financeflow.entity.Budget;
import com.mmf.financeflow.entity.BudgetCategory;

import java.util.List;

public interface BudgetService {
    Budget createBudget(BudgetRequest budgetRequest, String username);

    List<Budget> getBudgets(String username);

    List<Budget> getBudgetsByCategory(String username, BudgetCategory category);
}
