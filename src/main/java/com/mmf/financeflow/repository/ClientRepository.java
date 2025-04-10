package com.mmf.financeflow.repository;

import com.mmf.financeflow.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT account FROM Account account WHERE account.client.username = :username")
    List<Account> findAccountsByUsername(@Param("username") String username);

    @Query("SELECT income FROM Income income WHERE income.client.username = :username")
    List<Income> findIncomesByUsername(@Param("username") String username);

    @Query("SELECT expense FROM Expense expense WHERE expense.client.username = :username")
    List<Expense> findExpensesByUsername(@Param("username") String username);

    @Query("SELECT budget FROM Budget budget WHERE budget.client.username = :username")
    List<Budget> findBudgetsByUsername(@Param("username") String username);

    @Query("SELECT expense FROM Expense expense WHERE expense.client.username = :username AND expense.category = :category")
    List<Expense> findExpensesByUsernameAndCategory(@Param("username") String username, @Param("category") BudgetCategory category);

    @Query("SELECT budget FROM Budget budget WHERE budget.client.username = :username AND budget.category = :category")
    List<Budget> findBudgetsByUsernameAndCategory(String username, BudgetCategory category);
}
