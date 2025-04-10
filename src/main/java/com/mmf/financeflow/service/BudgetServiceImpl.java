package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.BudgetRequest;
import com.mmf.financeflow.entity.Account;
import com.mmf.financeflow.entity.Budget;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.exception.InsufficientBalanceException;
import com.mmf.financeflow.exception.InvalidAmountException;
import com.mmf.financeflow.exception.MismatchedCategoryException;
import com.mmf.financeflow.repository.BudgetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private ClientService clientService;
    private BudgetRepository budgetRepository;

    @Override

    public Budget create(BudgetRequest request, String username) {
        double budgetAmount = request.getAmount();
        BudgetCategory budgetCategory = request.getCategory();
        Budget budget = new Budget(request.getAmount(), request.getCategory());

        Client client = clientService.findByUsername(username);
        double unallocatedMoney = client.getUnallocatedMoney();

        if (budgetAmount <= 0) {
            throw new InvalidAmountException("Budget amount should be greater than 0!");
        }

        if (budgetAmount > unallocatedMoney) {
            throw new InsufficientBalanceException("Budget amount " + budgetAmount + " is more than unallocated budget: " + unallocatedMoney);
        }

        Account account = client.findAccountWithCategory(budgetCategory)
                .orElseThrow(() -> new MismatchedCategoryException("Account with category " + budgetCategory + " does not exist"));

        account.increaseBalance(budgetAmount);
        client.decreaseUnallocatedBudget(budgetAmount);

        client.addBudget(budget);
        clientService.update(client);
        return budget;
    }

    @Override
    public List<Budget> findAll(String username) {
        return budgetRepository.findBudgetsByUsername(username);
    }

    @Override
    public List<Budget> findByCategory(String username, BudgetCategory category) {
        return budgetRepository.findBudgetsByUsernameAndCategory(username, category);
    }
}
