package com.mmf.financeflow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class IncomeRequest {
    private double amount;
    private LocalDateTime dateTime;
    private String description;
}
