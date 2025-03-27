package com.mmf.financeflow.service;

import com.mmf.financeflow.entity.Budget;
import com.mmf.financeflow.repository.BudgetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public Budget create(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }
}
