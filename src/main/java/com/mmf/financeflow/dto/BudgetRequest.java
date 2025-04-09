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
public class BudgetRequest {
    private double amount;
    private LocalDateTime dateTime;
    private BudgetCategory category;

    public BudgetRequest(double amount, BudgetCategory category) {
        this.amount = amount;
        this.category = category;
        this.dateTime = LocalDateTime.now();
    }
}
