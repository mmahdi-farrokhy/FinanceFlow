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

    @Query("SELECT account FROM Account account WHERE account.client.username = :username AND account.category = :category")
    Account findAccountByUsernameAndCategory(String username, BudgetCategory category);
}
