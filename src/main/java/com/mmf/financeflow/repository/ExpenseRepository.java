package com.mmf.financeflow.repository;

import com.mmf.financeflow.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
