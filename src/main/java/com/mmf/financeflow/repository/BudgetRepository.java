package com.mmf.financeflow.repository;

import com.mmf.financeflow.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
