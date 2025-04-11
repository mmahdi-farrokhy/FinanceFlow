package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.IncomeRequest;
import com.mmf.financeflow.entity.Income;

import java.util.List;

public interface IncomeService {
    Income create(IncomeRequest request, String username);

    List<Income> findAll(String username);
}
