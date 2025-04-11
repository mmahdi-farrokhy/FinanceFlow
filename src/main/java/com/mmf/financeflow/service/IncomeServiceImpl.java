package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.IncomeRequest;
import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.entity.Income;
import com.mmf.financeflow.exception.InvalidAmountException;
import com.mmf.financeflow.repository.IncomeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IncomeServiceImpl implements IncomeService {
    private ClientService clientService;
    private IncomeRepository incomeRepository;

    @Override
    public Income create(IncomeRequest request, String username) {
        double incomeAmount = request.getAmount();
        String incomeDescription = request.getDescription();
        Income income = new Income(incomeAmount, incomeDescription);

        Client client = clientService.findByUsername(username);

        if (incomeAmount <= 0) {
            throw new InvalidAmountException("Income amount should be greater than 0!");
        }

        client.increaseUnallocatedBudget(incomeAmount);
        client.addIncome(income);
        clientService.update(client);
        return income;
    }

    @Override
    public List<Income> findAll(String username) {
        return incomeRepository.findIncomesByUsername(username);
    }
}
