package com.mmf.financeflow.service;

import com.mmf.financeflow.entity.Budget;

import java.util.List;

public interface BudgetService {
    Budget create(Budget budget);

    List<Budget> findAll();
}
