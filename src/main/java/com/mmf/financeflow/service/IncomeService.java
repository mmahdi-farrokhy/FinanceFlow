package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.IncomeRequest;
import com.mmf.financeflow.entity.Income;

import java.util.List;

public interface IncomeService {
    Income createIncome(IncomeRequest request, String username);

    List<Income> getIncomes(String username);
}
