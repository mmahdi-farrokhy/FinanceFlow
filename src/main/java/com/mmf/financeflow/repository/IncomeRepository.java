package com.mmf.financeflow.repository;

import com.mmf.financeflow.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query("SELECT income FROM Income income WHERE income.client.username = :username")
    List<Income> findIncomesByUsername(String username);
}
