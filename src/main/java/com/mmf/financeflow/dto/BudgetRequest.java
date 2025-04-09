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
    private BudgetCategory category;
}
