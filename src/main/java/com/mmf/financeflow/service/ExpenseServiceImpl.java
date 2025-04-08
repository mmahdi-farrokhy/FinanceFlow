package com.mmf.financeflow.service;

import com.mmf.financeflow.entity.Expense;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private ExpenseRepository expenseRepository;

    @Override
    public Expense create(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }
}
