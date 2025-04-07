package com.mmf.financeflow.service;

import com.mmf.financeflow.entity.Income;
import com.mmf.financeflow.repository.IncomeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IncomeServiceImpl implements IncomeService {
    private IncomeRepository incomeRepository;

    @Override
    public Income create(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> findAll() {
        return incomeRepository.findAll();
    }
}
