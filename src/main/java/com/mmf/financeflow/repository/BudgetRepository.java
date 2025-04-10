package com.mmf.financeflow.repository;

import com.mmf.financeflow.entity.Budget;
import com.mmf.financeflow.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query("SELECT budget FROM Budget budget WHERE budget.client.username = :username")
    List<Budget> findBudgetsByUsername(@Param("username") String username);

    @Query("SELECT budget FROM Budget budget WHERE budget.client.username = :username AND budget.category = :category")
    List<Budget> findBudgetsByUsernameAndCategory(String username, BudgetCategory category);
}
