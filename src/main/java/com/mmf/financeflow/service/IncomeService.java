package com.mmf.financeflow.service;

import com.mmf.financeflow.entity.Income;

import java.util.List;

public interface IncomeService {

    Income create(Income income);

    List<Income> findAll();
}
