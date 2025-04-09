package com.mmf.financeflow.dto;

import com.mmf.financeflow.entity.BudgetCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {
    private double amount;
    private String description;
    private BudgetCategory category;
    private String username;
}
