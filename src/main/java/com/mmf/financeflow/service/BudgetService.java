package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.BudgetRequest;
import com.mmf.financeflow.entity.Budget;
import com.mmf.financeflow.entity.BudgetCategory;

import java.util.List;

public interface BudgetService {
    Budget create(BudgetRequest budgetRequest, String username);

    List<Budget> findAll(String username);

    List<Budget> findByCategory(String username, BudgetCategory category);
}
