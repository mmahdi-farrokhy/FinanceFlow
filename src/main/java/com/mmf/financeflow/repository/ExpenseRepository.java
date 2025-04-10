package com.mmf.financeflow.repository;

import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT expense FROM Expense expense WHERE expense.client.username = :username")
    List<Expense> findExpensesByUsername(@Param("username") String username);

    @Query("SELECT expense FROM Expense expense WHERE expense.client.username = :username AND expense.category = :category")
    List<Expense> findExpensesByUsernameAndCategory(@Param("username") String username, @Param("category") BudgetCategory category);
}
